package huyng.utils;

import java.util.List;

import static java.lang.Math.sqrt;

public class StatisticHelper {
    public static double getMean(List<Double> x, List<Integer> counts) {
        double totalCount = counts.stream().mapToDouble(a -> a).sum();
        double sum = 0;
        for (int i = 0; i < x.size(); i++) {
            sum += x.get(i) * counts.get(i);
        }
        return sum / totalCount;
    }

    public static double getStandardDeviation(List<Double> x, List<Integer> counts, double mean) {
//        double variance = processors.stream()
//                .mapToDouble(p -> (p.getCore() - mean) * (p.getCore() - mean) * p.getCount())
//                .sum() / (totalCount - 1);
        double totalCount = counts.stream().mapToDouble(a -> a).sum();
        double sum = 0;
        for (int i = 0; i < x.size(); i++) {
            sum += (x.get(i) - mean) * (x.get(i) - mean) * counts.get(i);
        }
        return sqrt(sum / (totalCount - 1));
    }

    public static double getMarkWithStandardDistribution(double mean, double standardDeviation, double x){
        return (x - mean) / standardDeviation;
    }

}
