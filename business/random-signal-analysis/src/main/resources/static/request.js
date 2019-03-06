function autoFill() {

    var xhr = new XMLHttpRequest();
    var n = document.getElementById("input1");
    var frequency = document.getElementById("input4");
    var pointsAmount = document.getElementById("input3");

    xhr.open("POST", "/chart", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    var body = JSON.stringify({
        "amount_of_harmonic": +n.value,
        "points_amount": +pointsAmount.value,
        "frequency": +frequency.value
    });
    console.log(body);
    xhr.send(body);

    xhr.onreadystatechange = (e) =>
    {
        var pointsXArr = [];
        var pointsYArr = [];
        var pointsMutualCorr = [];
        var pointsAutoCorr = [];
        var pointsXYCorr = [];

        var jsonData = JSON.parse(xhr.responseText);
        // var rgbX = jsonData.rgb_x;
        // var rgbY = jsonData.rgb_y;

        for (var i = 0; i < jsonData.rxy_corr.length; i++) {
            var pointA = jsonData.rxy_corr[i];
            pointsMutualCorr.push({
                label: pointA.time,
                x: pointA.time,
                y: pointA.value
            });
        }
        for (var i1 = 0; i1 < jsonData.rzz_auto.length; i1++) {
            var pointB = jsonData.rzz_auto[i1];
            pointsAutoCorr.push({
                label: pointB.time,
                x: pointB.time,
                y: pointB.value
            });
        }

        var chart1 = new CanvasJS.Chart("chartContainer", {

            axisX: {
                tickColor: "red",
                title: "N",
                reversed: false
            },
            axisY: {
                title: "t"
            },
            title: {
                text: "Rxy vs Rzz"
            },

            data: [
                {
                    color: "red",
                    type: "line",
                    showInLegend: true,
                    name: "Mutual correlation",
                    dataPoints: pointsMutualCorr
                }
                ,
                {
                    color: "blue",
                    type: "line",
                    name: "Auto correlation",
                    showInLegend: true,
                    dataPoints: pointsAutoCorr
                }
            ]
        });

        chart1.render();
        // for (var i = 0; i < jsonData.points_x.length; i++) {
        //     var point = jsonData.points_x[i];
        //     pointsXArr.push({
        //         label: point.time,
        //         x: point.time,
        //         y: point.value
        //     });
        //     // mathE.push({
        //     //     label: point.time,
        //     //     x: point.time,
        //     //     y: jsonData.mathematical_expectation
        //     // });
        // }
        // for (var i4 = 0; i4 < jsonData.points_xycorr.length; i4++) {
        //     var point4 = jsonData.points_xycorr[i4];
        //     pointsXYCorr.push({
        //         label: point4.time,
        //         x: point4.time,
        //         y: point4.value
        //     });
        //     // mathE.push({
        //     //     label: point.time,
        //     //     x: point.time,
        //     //     y: jsonData.mathematical_expectation
        //     // });
        // }

        // for (var i1 = 0; i1 < jsonData.points_xauto_corr.length; i1++) {
        //     var pointA = jsonData.points_xauto_corr[i1];
        //     pointsMutualCorr.push({
        //         label: pointA.time,
        //         x: pointA.time,
        //         y: pointA.value
        //     });
        // }
        //
        // for (var i2 = 0; i2 < jsonData.points_x.length; i2++) {
        //     var point2 = jsonData.points_y[i2];
        //     pointsYArr.push({
        //         label: point2.time,
        //         x: point2.time,
        //         y: point2.value
        //     });
        // }
        //
        // for (var i3 = 0; i3 < jsonData.points_xauto_corr.length; i3++) {
        //     var point3 = jsonData.points_xauto_corr[i3];
        //     pointsAutoCorr.push({
        //         label: point3.time,
        //         x: point3.time,
        //         y: point3.value
        //     });
        // }

        // document.getElementById("math").value = jsonData.mathematical_expectationX;
        // document.getElementById("disp").value = jsonData.dispersionY;
        // console.log(pointsXArr);
        // var chart1 = new CanvasJS.Chart("chartContainer", {
        //
        //     axisX: {
        //         tickColor: "red",
        //         title: "time, t",
        //         reversed: false
        //     },
        //     axisY: {
        //         title: "function, x(t)"
        //     },
        //     title: {
        //         text: "Harmonic data x(t)"
        //     },
        //
        //     data: [
        //         {
        //             color: rgbX,
        //             type: "area",
        //             dataPoints: pointsXArr
        //         }
        //         // ,
        //         // {
        //         //     type: "line",
        //         //     dataPoints: mathE
        //         // }
        //     ]
        // });
        //
        // var chart2 = new CanvasJS.Chart("chartContainer1", {
        //     axisX: {
        //         tickColor: "red",
        //         title: "time, t",
        //         reversed: false
        //     },
        //     axisY: {
        //         title: "function, x(t)"
        //     },
        //     title: {
        //         text: "Autocorrelation R(x,x)(t)"
        //     },
        //
        //     data: [
        //         {
        //             color: rgbX,
        //             type: "area",
        //             dataPoints: pointsMutualCorr
        //         }
        //     ]
        // });
        //
        // var chart3 = new CanvasJS.Chart("chartContainer2", {
        //     axisX: {
        //         tickColor: "red",
        //         title: "time, t",
        //         reversed: false
        //     },
        //     axisY: {
        //         title: "function, y(t)"
        //     },
        //     title: {
        //         text: "Harmonic data y(t)"
        //     },
        //
        //     data: [
        //         {
        //             color: rgbY,
        //             type: "area",
        //             dataPoints: pointsYArr
        //         }
        //     ]
        // });
        // var chart4 = new CanvasJS.Chart("chartContainer3", {
        //     axisX: {
        //         tickColor: "red",
        //         title: "time, t",
        //         reversed: false
        //     },
        //     axisY: {
        //         title: "function, y(t)"
        //     },
        //     title: {
        //         text: "Autocorrelation R(y,y)(t)"
        //     },
        //
        //     data: [
        //         {
        //             color: rgbY,
        //             type: "area",
        //             dataPoints: pointsAutoCorr
        //         }
        //     ]
        // });
        //
        // var chart5 = new CanvasJS.Chart("chartContainer4", {
        //     axisX: {
        //         tickColor: "red",
        //         title: "time, t",
        //         reversed: false
        //     },
        //     axisY: {
        //         title: "function"
        //     },
        //     title: {
        //         text: "Mutual correlation R(x,y)(t)"
        //     },
        //
        //     data: [
        //         {
        //             color: "greenShades",
        //             type: "area",
        //             dataPoints: pointsXYCorr
        //         }
        //     ]
        // });

        // chart1.render();
        // chart2.render();
        // chart3.render();
        // chart4.render();
        // chart5.render();
    }
}