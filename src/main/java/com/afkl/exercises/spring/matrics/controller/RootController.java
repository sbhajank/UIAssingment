package com.afkl.exercises.spring.matrics.controller;

import com.afkl.exercises.spring.matrics.ApiStatusSummery;
import com.afkl.exercises.spring.matrics.metric.IMetricService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RootController {


    @Autowired
    private IMetricService metricService;

    public RootController() {
        super();
    }


    // API

    @RequestMapping(value = "/metric", method = RequestMethod.GET)
    @ResponseBody
    public List<ApiStatusSummery> getMetric() {
        final Map<String, Map<Integer, Integer>> fullMetric = metricService.getFullMetric();
        List<ApiStatusSummery> summeries = new ArrayList<>();
        fullMetric.forEach((s, info) ->{
            final String[] split = s.split(" ");
            if(StringUtils.contains(split[1],"/fares/")){
                ApiStatusSummery apiStatusSummery = new ApiStatusSummery();
                apiStatusSummery.setMethod(split[0]);
                apiStatusSummery.setPath(split[1]);
                final Map.Entry<Integer, Integer> entry
                        = info.entrySet().stream().findFirst().orElse(null);
                if(entry != null){
                    apiStatusSummery.setStatus(entry.getKey());
                    apiStatusSummery.setCount(entry.getValue());
                }
                summeries.add(apiStatusSummery);
            }

        });
        return summeries;
    }

    @RequestMapping(value = "/status-metric", method = RequestMethod.GET)
    @ResponseBody
    public List<ApiStatusSummery> getStatusMetric() {
        final Map<Integer, Integer> statusMetric = metricService.getStatusMetric();
        return statusMetric.entrySet().stream().map(entry-> new ApiStatusSummery(null,null,entry.getKey(), entry.getValue())).collect(Collectors.toList());

    }

    @RequestMapping(value = "/metric-graph", method = RequestMethod.GET)
    @ResponseBody
    public Object[][] drawMetric() {
        final Object[][] result = metricService.getGraphData();
        for (int i = 1; i < result[0].length; i++) {
            result[0][i] = result[0][i].toString();
        }
        return result;
    }


}
