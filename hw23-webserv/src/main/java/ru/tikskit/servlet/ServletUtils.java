package ru.tikskit.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

class ServletUtils {
    public static String getLoginPageContent(String commonResourceDir, String templateFileName) throws IOException {
        Path indexPath = Paths.get(commonResourceDir, templateFileName);

        try (InputStream is = LoginServlet.class.getClassLoader().getResourceAsStream(indexPath.toString())) {
            return readFileContent(Objects.requireNonNull(is));
        }
    }

    private static String readFileContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8);
    }
}
