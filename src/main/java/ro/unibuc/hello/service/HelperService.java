package ro.unibuc.hello.service;

import java.util.Random;

public class HelperService {
    public static String generateRandomIp() {
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }
}
