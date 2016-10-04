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
            $(document).ready(function(){
                /*$('.ui-datepicker-calendar td')
                    .each(function(index, elem)
                    {
                        if (!$(this).hasClass("ui-datepicker-other-month")){
                            $(this).find('.ui-state-default').eq(0).css('background', '#0f93ff');                                                     
                        }
                    }
                );*/
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
                            //аjax-запрос на анлиз некоторой даты
                        },
                        onChangeMonthYear: function (year, month, inst) {
                            console.log(year + " " + month);
                            //аjax-запрос на получение дат
                        }
                    }
                );
                //$.datepicker.setDefaults($.datepicker.regional['ru']);
            });            
        </script>
    </head>
    <body>
        <h1>Отчет по траектории движения автобусов</h1>
        
        <!--<p>Дата: <input type="text" id="datepicker"></p>-->
        
        <div id="datepicker"></div>
    </body>
</html>
