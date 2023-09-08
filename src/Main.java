import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger threeLetter = new AtomicInteger();
    public static AtomicInteger fourLetter = new AtomicInteger();
    public static AtomicInteger fiveLetter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable palidromMethod = () -> {
            for (String str : texts) {
                int length = str.length();
                int j = 0;
                int forward = 0;
                int backward = length - 1;
                while (backward > forward) {
                    char forwardChar = str.charAt(forward++);
                    char backwardChar = str.charAt(backward--);
                    if (forwardChar != backwardChar) {
                        j = 0;
                        break;
                    }
                    j = 1;
                }
                if (j == 1) {
                    if (str.length() == 3) threeLetter.incrementAndGet();
                    else if (str.length() == 4) fourLetter.incrementAndGet();
                    else if (str.length() == 5) fiveLetter.incrementAndGet();
                }
            }
        };

        Runnable sameLetterMethod = () -> {
            for (String str : texts) {
                int i = 0;
                int j = 0;
                while (i < str.length() - 1) {
                    if (str.charAt(i) == str.charAt(i + 1)) {
                        j = 1;
                        i++;
                    } else {
                        j = 0;
                        break;
                    }
                }
                if (j == 1) {
                    if (str.length() == 3) threeLetter.incrementAndGet();
                    else if (str.length() == 4) fourLetter.incrementAndGet();
                    else if (str.length() == 5) fiveLetter.incrementAndGet();
                }
            }
        };

        Runnable upLetterMethod = () -> {
            for (String str : texts) {
                int i = 0;
                int j = 0;
                while (i < str.length() - 1) {
                    if (str.charAt(i) <= str.charAt(i + 1)) {
                        j = 1;
                    } else {
                        j = 0;
                        break;
                    }
                    i++;
                }
                if (j == 1) {
                    if (str.length() == 3) threeLetter.incrementAndGet();
                    else if (str.length() == 4) fourLetter.incrementAndGet();
                    else if (str.length() == 5) fiveLetter.incrementAndGet();
                }
            }
        };

        Thread threadSameLetter = new Thread(sameLetterMethod);
        Thread threadPalidrom = new Thread(palidromMethod);
        Thread threadUpLetter = new Thread(upLetterMethod);

        threadSameLetter.start();
        threadPalidrom.start();
        threadUpLetter.start();

        threadSameLetter.join();
        threadPalidrom.join();
        threadUpLetter.join();

        System.out.println("Красивых слов с длиной 3: " + threeLetter.get());
        System.out.println("Красивых слов с длиной 4: " + fourLetter.get());
        System.out.println("Красивых слов с длиной 5: " + fiveLetter.get());
    }

    public static String generateText(String fiveLetter, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(fiveLetter.charAt(random.nextInt(fiveLetter.length())));
        }
        return text.toString();
    }
}