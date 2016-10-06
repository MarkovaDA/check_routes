<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Отчет по маршрутам автобусов</title>
        <link rel="stylesheet" href="<c:url value="resources/css/jquery-ui.css"/>">
        <script src="<c:url value="resources/js/jquery.js"/>"></script>
        <script src="<c:url value="resources/js/jquery-ui.js"/>"></script>
        <script src="<c:url value="resources/js/datepicker.localization.js"/>"></script>
        <style>
        .ui-datepicker-calendar td {
            padding: 3px !important;
        }
        </style>
        
        
        <script>
            function selectDaysOfMonths(arrayOfNumbers){
                $('.ui-datepicker-calendar td')
                    .each(function(index, elem)
                    {   
                        if (!$(this).hasClass("ui-datepicker-other-month")){
                            var value = parseInt($(this).find('.ui-state-default').eq(0).text());
                            if (arrayOfNumbers.includes(value))
                            $(this).find('.ui-state-default').eq(0).css('border', '1px solid orange');                                                     
                        }
                    }
                );
            }
            $(document).ready(function(){
               
                $("#datepicker").datepicker(
                    {
                        changeMonth:true,
                        changeYear:true,
                        dateFormat: "yy-mm-dd",
                        closeText: 'Закрыть',
                        prevText: '&#x3c;Пред',
                        nextText: 'След&#x3e;',
                        currentText: 'Сегодня',
                        monthNames: ['Январь','Февраль','Март','Апрель','Май','Июнь',
                        'Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'],
                        monthNamesShort: ['Янв','Фев','Мар','Апр','Май','Июн',
                        'Июл','Авг','Сен','Окт','Ноя','Дек'],
                        dayNames: ['воскресенье','понедельник','вторник','среда','четверг','пятница','суббота'],
                        dayNamesShort: ['вск','пнд','втр','срд','чтв','птн','сбт'],
                        dayNamesMin: ['Вс','Пн','Вт','Ср','Чт','Пт','Сб'],
                        firstDay: 1,
                        isRTL: false,
                        onSelect: function (dateText, inst) {
                            console.log(dateText);
                        },
                        onChangeMonthYear: function (year, month, inst) {
                            $('#load').fadeIn(200);
                            var vechicleId = $('#vechicle_id').val();
                            console.log(vechicleId);
                            $.get("report_api/get_days_of_months?year="+year+"&month="+month + "&vechicle_id="+vechicleId, function(data){
                                console.log(data);
                                $('#load').fadeOut(100);
                                selectDaysOfMonths(data);
                            });
                        }
                    }
                );
                $('#load').hide();
                //$.datepicker.setDefaults($.datepicker.regional['ru']);
            });            
        </script>
    </head>
    <body>
        
        
        <!--<p>Дата: <input type="text" id="datepicker"></p>-->
        <div style="width: 100%; text-align: left;">
            <h1>Отчет по траектории движения автобусов</h1>
            <p>Укажите идентификатор транспорта (1 ... 6544)</p>
            <input type="text" id="vechicle_id" value="1177"><br>
            <br>
            <div id="datepicker"></div>
            <br>
            <div id="load" style="width:300px; text-align: center;">
                <img src="<c:url value="resources/image/loader.gif"/>" width="50px">
            </div>
            <p>Укажите временной диапазон для отчета</p>
            <input type="text" value="****-**-**" id="date1"> - 
            <input type="text" value="****-**-**" id="date2">
        </div>
    </body>
</html>
