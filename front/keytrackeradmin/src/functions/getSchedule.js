import routes from '../routes.json'

export function getWeek(currentDate){
    var currentDay = currentDate.getDay();
    console.log(currentDay, currentDate.toISOString().slice(0, 10)) // Получаем номер текущего дня недели (0 - воскресенье, 1 - понедельник, ..., 6 - суббота)
    
    var firstDayOfWeek = new Date(currentDate); // Создаем копию объекта даты
    firstDayOfWeek.setDate(currentDate.getDate() - currentDay); // Устанавливаем первый день недели (воскресенье)
    console.log(firstDayOfWeek.toISOString().slice(0, 10))

    var dates = [];
    for (var i = 0; i < 7; i++) {
        var date = new Date(firstDayOfWeek);
        date.setDate(firstDayOfWeek.getDate() + i);
        dates.push(date);
    }

    console.log(dates)
    return dates;
}

async function fetchSchedule(scheduleRoute){
    const token = localStorage.getItem('token');
    try {
        const response = await fetch(scheduleRoute, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
}

export async function getSchedule(id, currentDate){
    const scheduleRoute = routes.host + routes.schedule + "?Id=" + id;

    let schedule = await fetchSchedule(scheduleRoute)
    const dates = await getWeek(currentDate);

    let dateStrings = ["", "", "", "", "", "", "" ]

    for(let i = 0; i < dateStrings.length; i++){
        dateStrings[i] = dates[i].toISOString().slice(0, 10) + "T00:00:00"
    }

    console.log(dateStrings)

    schedule = schedule.filter(s => dateStrings.includes(s.date))
    console.log(schedule)
    return schedule
}