import { Component } from "@angular/core";

var d3: any=require('d3');

@Component({
    moduleId:    module.id,
    selector:    'device-counter-chart',
    templateUrl: 'device-counter.component.html',
    styleUrls:     ['device-counter.component.css']
})

export class DeviceCounterComponent{

    constructor():void {
        this.drawSvg();

    }


    private drawSvg():void{
        var margin = {top: 20, right: 20, bottom: 80, left: 40},
            width = 1200 - margin.left - margin.right,
            height = 400 - margin.top - margin.bottom;

        var x0 = d3.scale.ordinal().rangeRoundBands([0, width], 1); //if .1 the line will not be aligned with date y axis

        var y0 = d3.scale.linear().range([height, 0]);

        var color = d3.scale.ordinal().range(["#ff8c00", "#a05d56"]);

        var xAxis = d3.svg.axis()
            .scale(x0)
            .orient("bottom").tickSize(-height).tickSubdivide(true);

        var yAxis = d3.svg.axis()
            .scale(y0)
            .orient("left")
            .tickFormat(d3.format(".2s"));

        var svg = d3.select("body").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        d3.json("http://localhost:8080/ycweb-1.0/ckp/all", function(error, data) {
          if (error) throw error;

          data = data.dclist;

          var counterNames = d3.keys(data[0]).filter(function(key) { return key !== "eventDate"; });

          data.forEach(function(d) {
            d.counters = counterNames.map(function(name) { return {name: name, value: +d[name]}; });
          });


          x0.domain(data.map(function(d) { return d.eventDate; }));
          y0.domain([0, d3.max(data, function(d) { return d3.max(d.counters, function(d) { return d.value; }); })]);

          svg.append("g")
              .attr("class", "x axis")
              .attr("transform", "translate(0," + height + ")")
              .call(xAxis)
              .selectAll("text")
              .style("text-anchor", "end")
              //.attr("dx", "-.8em")
              //.attr("dy", ".15em")
              .attr("transform", "rotate(-65)" );
              ;

          svg.append("g")
              .attr("class", "y axis")
              .call(yAxis)
              .append("text")
              .attr("transform", "rotate(-90)")
              .attr("y", 6)
              .attr("dy", ".71em")
              .style("text-anchor", "end")
              .text("Device Counter");

          var state = svg.selectAll(".state")
              .data(data)
              .enter().append("g")
              .attr("class", "state")
              .attr("transform", function(d) { return "translate(" + x0(d.eventDate) + ",0)"; })
              ;


            var line1 = d3.svg.line()
                .x(function(d) {
                    return x0(d.eventDate);
                })
                .y(function(d) {
                    return y0(d.uniqueCounter);
                })

            // create a line function that can convert data[] into x and y points
            var line2 = d3.svg.line()
                .x(function(d) {
                    return x0(d.eventDate);
                })
                .y(function(d) {
                    return y0(d.allCounter);
                    })

            // add lines
            //bind data: do this AFTER the axes above so that the line is above the tick-lines
            svg.append("svg:path").attr("d", line1(data)).attr("class", "data1");
            svg.append("svg:path").attr("d", line2(data)).attr("class", "data2");

            var circle0 = svg.selectAll("circle")
                  .data(data)
                  .enter().append("circle")
                  .attr("cx", function(d, i) { return x0(d.eventDate); })
                  .attr("cy", function(d, i) { return y0(d.allCounter); })
                  .attr("r", 3);

            var circle1 = svg.selectAll("circle1")
                  .data(data)
                  .enter().append("circle")
                  .attr("cx", function(d) { return x0(d.eventDate); })
                  .attr("cy", function(d) { return y0(d.uniqueCounter); })
                  .attr("r", 3);

        ////////////////////////

            svg.selectAll("circle.text0")
                  .data(data)
                  .enter().append("text")
                  .attr("x", function(d) { return x0(d.eventDate); })
                  .attr("y", function(d) { return y0(d.allCounter); })
                  .text(function(d) { return d.all; });

            svg.selectAll("circle.text1")
                  .data(data)
                  .enter().append("text")
                  .attr("x", function(d) { return x0(d.eventDate); })
                  .attr("y", function(d) { return y0(d.uniqueCounter); })
                  .text(function(d) { return d.unique; });


          var legend = svg.selectAll(".legend")
              .data(counterNames.slice().reverse())
              .enter().append("g")
              .attr("class", "legend")
              .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

          legend.append("rect")
              .attr("x", width - 1)
              .attr("width", 18)
              .attr("height", 1)
              .style("fill", color);

          legend.append("text")
              .attr("x", width - 4)
              .attr("y", 9)
              .attr("dy", ".35em")
              .style("text-anchor", "end")
              .text(function(d) { return d; });

        });


    }

}