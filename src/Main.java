import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    final static String Letters = "RLRFR";
    final static int RouteLength = 100;
    final static int AmountOfThread = 1000;

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < AmountOfThread; i++) {
            new Thread(() -> {
                String route = generateRoute(Letters, RouteLength);
                int frequency = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frequency)) {
                        sizeToFreq.put(frequency, sizeToFreq.get(frequency) + 1);
                    } else {
                        sizeToFreq.put(frequency, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> maxCount = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + maxCount.getKey()
                + " Встретилось (" + maxCount.getValue() + " раз )");

        System.out.println("Другие размеры :");
        sizeToFreq.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(к -> System.out.println(" - " + к.getKey() + " (" + к.getValue() + " раз)"));
    }
}
