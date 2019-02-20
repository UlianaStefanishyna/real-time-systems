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

    xhr.onreadystatechange = (e) =>    {
        var arr = [];
        var mathE = [];
        console.log(xhr.responseText);
        var jsonData = JSON.parse(xhr.responseText);
        for (var i = 0; i < jsonData.points.length; i++) {
            var point = jsonData.points[i];
            arr.push({
                label: point.time,
                x: point.time,
                y: point.value
            });
            mathE.push({
                label: point.time,
                x: point.time,
                y: jsonData.mathematical_expectation
            });
        }
        document.getElementById("math").value = jsonData.mathematical_expectation;
        document.getElementById("disp").value = jsonData.dispersion;
        console.log(arr);
        var chart1 = new CanvasJS.Chart("chartContainer", {

            axisX: {
                tickColor: "red",
                title: "time, t"
            },
            axisY: {
                title: "function, x(t)"
            },
            title: {
                text: "Harmonics data"
            },

            data: [
                {
                    type: "area", // "area", "column"
                    dataPoints: arr
                },
                {
                    type: "line",
                    dataPoints: mathE
                }
            ]
        });
        chart1.render();
    }
}