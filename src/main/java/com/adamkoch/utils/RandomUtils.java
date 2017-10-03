package com.adamkoch.utils;

import com.google.common.base.Suppliers;
import com.google.common.io.Files;
import org.apache.commons.lang3.ClassPathUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p>Created by aakoch on 2017-07-31.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class RandomUtils {
    private static final Logger LOGGER = LogManager.getLogger(RandomUtils.class);

    private static Random RANDOM = new Random();
    private static com.google.common.base.Supplier<Integer>
            supplier = Suppliers.memoizeWithExpiration(() -> {
        LOGGER.info("getting line count");
        try {
            return countLines("names2.txt");
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }, 1, TimeUnit.MINUTES);

    public static <T> T removeRandom(List<T> list) {
        return list.remove(RANDOM.nextInt(list.size()));
    }

    public static <T> T getRandom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static int nextInt(int inclusive, int exclusive) {
        return RANDOM.nextInt(exclusive - inclusive) + inclusive;
    }

    public static <T> T getRandom(Set<T> set) {
        return getRandom(new ArrayList<T>(set));
    }

    public static String randomName() {
        try {
            int lineCount = supplier.get();
            LOGGER.trace("lineCount = " + lineCount);
            int rand = RANDOM.nextInt(lineCount - 1) + 1;
            String line = readLine("names2.txt", rand);
            LOGGER.trace("line = " + line);
            return line;
        }
        catch (IOException e) {
            LOGGER.error("Error reading file: " + e.getMessage(), e);
            return "Adam";
        }
    }

    public static int countLines(String filename) throws IOException {
        File file = getFile(filename);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
        finally {
            is.close();
        }
    }

    public static File getFile(String filename) {
        ClassLoader classLoader = RandomUtils.class.getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }

    public static String readLine(String filename, int lineNumber) throws IOException {
        File file = getFile(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int counter = 0;
            for (String line; (line = br.readLine()) != null; ) {
                counter++;
                if (counter == lineNumber) {
                    return line;
                }
            }
        }
        return null;
    }
}
