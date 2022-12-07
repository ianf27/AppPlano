package fracalossi.ian.appplano.Util;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {
    /**
     * Realiza o download de uma imagem presente em um endereço URL fornecido e a salva no cache
     * @param context contexto da app
     * @param imageUrl endereço URL da imagem
     * @param imageView ImageView onde a imagem deverá aparecer na app
     * @param w largura que a imagem deve ter
     * @param h altura que a imagem deve ter
     */
    public static void loadImageUrlToImageView(Context context, URL imageUrl, ImageView imageView, int w, int h) {

        String path = imageUrl.getPath();
        String imageName = path.substring(path.lastIndexOf('/') + 1);
        String imageLocation = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + imageName;

        File f = new File(imageLocation);
        if (f.exists() && !f.isDirectory()) {
            imageView.setImageBitmap(Util.getBitmap(imageLocation));
        }
        else {
            MutableLiveData<Bitmap> imgLd = new MutableLiveData<>();

            imgLd.observe(getLifecycleOwner(context), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpRequest httpRequest = new HttpRequest(imageUrl.toString(), "GET", "UTF-8");

                    try {
                        InputStream is = httpRequest.execute();
                        Bitmap img = Util.inputStream2Bitmap(is);
                        httpRequest.finish();

                        Util.saveImage(img, imageLocation);

                        Bitmap finalImg = Util.getBitmap(imageLocation, w, h);
                        imgLd.postValue(finalImg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Realiza o download de uma imagem presente em um bd. A imagem é obtida via id e é salva no cache
     * @param context contexto da app
     * @param id id da imagem no bd do servidor
     * @param imageView ImageView onde a imagem deverá aparecer na app
     * @param w largura que a imagem deve ter
     * @param h altura que a imagem deve ter
     */
    public static void loadImageBase64ToImageView(Context context, String id, ImageView imageView, int w, int h) {

        String imageLocation = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + id;
        File f = new File(imageLocation);
        if (f.exists() && !f.isDirectory()) {
            imageView.setImageBitmap(Util.getBitmap(imageLocation));
        }
        else {
            MutableLiveData<Bitmap> imgLd = new MutableLiveData<>();
            imgLd.observe(getLifecycleOwner(context), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpRequest httpRequest = new HttpRequest(Config.GASTO_APP_URL + "pegar_imagem_produto.php", "GET", "UTF-8");
                    httpRequest.addParam("id", id);

                    try {
                        InputStream is = httpRequest.execute();
                        String imgBase64 = Util.inputStream2String(is, "UTF-8");
                        httpRequest.finish();

                        String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",") + 1);
                        Bitmap img = Util.base642Bitmap(pureBase64Encoded);
                        Util.saveImage(img, imageLocation);

                        Bitmap finalImg = Util.getBitmap(imageLocation, w, h);
                        imgLd.postValue(finalImg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private static LifecycleOwner getLifecycleOwner(Context context) {
        while (!(context instanceof LifecycleOwner)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (LifecycleOwner) context;
    }
}
