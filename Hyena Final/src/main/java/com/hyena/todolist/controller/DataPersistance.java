package com.hyena.todolist.controller;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.google.gson.Gson;
import com.hyena.todolist.model.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 *
 * @author k1715939
 */
public class DataPersistance {

    FileStorage fs = new FileStorage();

    public FileStorage getFs() {
        return fs;
    }

    public void setFs(FileStorage fs) {
        this.fs = fs;
    }

    public ArrayList<Task> load() throws IOException {
        String url = "http://nooblab.com/p2.json";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        Gson gson = new Gson();

        Task[] results = gson.fromJson(rd, Task[].class);
        ArrayList<Task> resultsAL = new ArrayList(Arrays.asList(results));
        fs.setCurrentTDL(resultsAL);
        return resultsAL;
    }

    public ArrayList<Task> loadFromDropBox() throws DbxException, IOException {

        try {
            final String ACCESS_TOKEN = "QSAPdu6YIvAAAAAAAAAApmE8B4GME-SQXIDTVl706GY84NBCUAEoa8qCq-mcE0il";
            // Create Dropbox client
            DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
            File f = new File("/Dropbox.json");
            if (f.exists() && !f.isDirectory()) {
                f.delete();
            }

            try (OutputStream out = new FileOutputStream("Dropbox.json")) {
                FileMetadata metadata = client.files().downloadBuilder("/Dropbox.json")
                        .download(out);
            }

        } catch (NullPointerException e) {
            System.out.println(e);
        }
        FileStorage fs = new FileStorage();
        return fs.loadFileDropBox();
    }

    public void saveToDropBox() throws DbxException, IOException {
        final String ACCESS_TOKEN = "QSAPdu6YIvAAAAAAAAAApmE8B4GME-SQXIDTVl706GY84NBCUAEoa8qCq-mcE0il";
        // Create Dropbox client
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        client.files().deleteV2("/Dropbox.json");
        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("Dropbox.json")) {
            FileMetadata metadata = client.files().uploadBuilder("/Dropbox.json")
                    .uploadAndFinish(in);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
