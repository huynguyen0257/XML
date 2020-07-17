package huyng.crawler;

import huyng.daos.LaptopDAO;
import huyng.daos.MonitorDAO;
import huyng.daos.ProcessorDAO;
import huyng.daos.RamDAO;
import huyng.entities.LaptopEntity;
import huyng.entities.MonitorEntity;
import huyng.entities.ProcessorEntity;
import huyng.entities.RamEntity;
import huyng.services.LaptopService;
import huyng.utils.StatisticHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;

public class StatisticMark implements Runnable {
    @Override
    public void run() {
        updateMonitorMark();
        updateRamMark();
        updateProcessorMark();
        updateWeightMark();
    }

    private void updateRamMark(){
        RamDAO ramDAO = new RamDAO();
        List<RamEntity> rams = ramDAO.findAllByNameQuery("Ram.getAll");

        List<Double> memories = rams.stream().map(p -> {
            return (double) p.getMemory();
        }).collect(Collectors.toList());
        List<Integer> counts = rams.stream().map(p -> {
            return p.getCount();
        }).collect(Collectors.toList());
        double core_mean = StatisticHelper.getMean(memories, counts);
        double core_standardDeviation = StatisticHelper.getStandardDeviation(memories,counts,core_mean);
        rams.forEach(p -> {
            double mark = StatisticHelper.getMarkWithStandardDistribution(core_mean,core_standardDeviation,p.getMemory());
            p.setMark(mark);
            ramDAO.updateMark(p);
        });
    }

    private void updateMonitorMark(){
        MonitorDAO monitorDAO = new MonitorDAO();
        List<MonitorEntity> monitors = monitorDAO.findAllByNameQuery("Monitor.findAll");

        List<Double> memories = monitors.stream().map(p -> {
            return (double) p.getSize();
        }).collect(Collectors.toList());
        List<Integer> counts = monitors.stream().map(p -> {
            return p.getCount();
        }).collect(Collectors.toList());
        double core_mean = StatisticHelper.getMean(memories, counts);
        double core_standardDeviation = StatisticHelper.getStandardDeviation(memories,counts,core_mean);
        monitors.forEach(p -> {
            double mark = StatisticHelper.getMarkWithStandardDistribution(core_mean,core_standardDeviation,p.getSize());
            p.setMark(mark);
            monitorDAO.updateMark(p);
        });
    }
    private void updateWeightMark(){
        LaptopService laptopService = new LaptopService();
        List<LaptopEntity> laptops = laptopService.getAll();

        List<Double> weights = laptops.stream().map(p -> {
            return (double) p.getWeight();
        }).collect(Collectors.toList());
        List<Integer> counts = new ArrayList<>();
        weights.forEach(w -> {
            counts.add(1);
        });
        double weight_mean = StatisticHelper.getMean(weights, counts);
        double weight_standardDeviation = StatisticHelper.getStandardDeviation(weights, counts, weight_mean);
        laptops.forEach(l -> {
            double weightMark = StatisticHelper.getMarkWithStandardDistribution(weight_mean, weight_standardDeviation, (l.getWeight() - 2 * (l.getWeight() - weight_mean)));
            l.setWeightMark(weightMark);
            laptopService.updateLaptopWegihtMark(l);
        });
    }

    private void updateProcessorMark(){
        ProcessorDAO processorDAO = new ProcessorDAO();
        List<ProcessorEntity> processors = processorDAO.findAllByNameQuery("Processor.findProcessorInUse");
        List<Integer> counts = processors.stream().map(p -> {
            return p.getCount();
        }).collect(Collectors.toList());
        //Core
        List<Double> cores = processors.stream().map(p -> {
            return (double) p.getCore();
        }).collect(Collectors.toList());
        double core_mean = StatisticHelper.getMean(cores, counts);
        double core_standardDeviation = StatisticHelper.getStandardDeviation(cores,counts,core_mean);

        //Thread
        List<Double> threads = processors.stream().map(p -> {
            return (double) p.getThread();
        }).collect(Collectors.toList());
        double thread_mean = StatisticHelper.getMean(threads, counts);
        double thread_standardDeviation = StatisticHelper.getStandardDeviation(threads,counts,thread_mean);

        //BaseClocks
        List<Double> baseClocks = processors.stream().map(p -> {
            return p.getBaseClock();
        }).collect(Collectors.toList());
        double baseClock_mean = StatisticHelper.getMean(baseClocks, counts);
        double baseClock_standardDeviation = StatisticHelper.getStandardDeviation(baseClocks,counts,baseClock_mean);

        //BoostClock
        List<Double> boostClocks = processors.stream().map(p -> {
            return p.getBoostClock();
        }).collect(Collectors.toList());
        double boostClock_mean = StatisticHelper.getMean(boostClocks, counts);
        double boostClock_standardDeviation = StatisticHelper.getStandardDeviation(boostClocks,counts,boostClock_mean);

        //CoreCaches
        List<Double> caches = processors.stream().map(p -> {
            return  p.getCache();
        }).collect(Collectors.toList());
        double cache_mean = StatisticHelper.getMean(caches, counts);
        double cache_standardDeviation = StatisticHelper.getStandardDeviation(caches,counts,cache_mean);

        processors.forEach(p -> {
            double mark = StatisticHelper.getMarkWithStandardDistribution(core_mean,core_standardDeviation,p.getCore());
            double thread = StatisticHelper.getMarkWithStandardDistribution(thread_mean,thread_standardDeviation,p.getThread());
            double baseClock = StatisticHelper.getMarkWithStandardDistribution(baseClock_mean,baseClock_standardDeviation,p.getBaseClock());
            double boostBlock = StatisticHelper.getMarkWithStandardDistribution(boostClock_mean,boostClock_standardDeviation,p.getBoostClock());
            double cache = StatisticHelper.getMarkWithStandardDistribution(cache_mean,cache_standardDeviation,p.getCache());
            p.setMark((mark+thread+baseClock+boostBlock+cache)/5);
            processorDAO.updateMark(p);
        });
    }
}
