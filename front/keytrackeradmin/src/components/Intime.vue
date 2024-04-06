<script setup>
import { useRouter, useRoute } from 'vue-router'
import Navbar from './Navbar.vue'
import { getWeek, getSchedule } from '../functions/getSchedule'
import { ref, onMounted } from 'vue';
import routes from '../routes.json'
const week = ref([])

const weekstr = ref("")
const isLoaded = ref(0)

const scheduleArr = ref([
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
        [{},{},{},{},{},{},{}], 
    ])

const lessonArr = ["First", "Second", "Third", "Fourth","Fifth","Sixth","Seventh"]



const route = useRoute()
const id = route.params.id;

let curDate = new Date()

async function previousWeek(){
    isLoaded.value = 0;
    curDate = new Date(curDate.getTime() - 7 * 24 * 60 * 60 * 1000);
    console.log(curDate)

    week.value = await getWeek(curDate)
    weekstr.value = week.value[1].toISOString().slice(0, 10) + " - " + week.value[6].toISOString().slice(0, 10)
    schedule.value = await getSchedule(id, curDate);

        for(let i = 1; i<week.value.length; i++){
        for(let l = 0; l<lessonArr.length; l++){
            scheduleArr.value[i][l] = schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00").length > 0 ? schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00")[0] : {}
        }
    }
    isLoaded.value = 1;
}

async function nextWeek(){
    isLoaded.value = 0;
    curDate = new Date(curDate.getTime() + 7 * 24 * 60 * 60 * 1000);
    console.log(curDate)

    week.value = await getWeek(curDate)
    weekstr.value = week.value[1].toISOString().slice(0, 10) + " - " + week.value[6].toISOString().slice(0, 10)
    schedule.value = await getSchedule(id, curDate);

    for(let i = 1; i<week.value.length; i++){
        for(let l = 0; l<lessonArr.length; l++){
            scheduleArr.value[i][l] = schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00").length > 0 ? schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00")[0] : {}
        }
    }
    isLoaded.value = 1;
}

function deleteCell(id){
    let token = localStorage.getItem("token")
    console.log(scheduleArr.value[6][0])
    fetch(routes.host+routes.deleteApp+"?Id="+id, {
            method: 'DELETE', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

const schedule = ref([])

onMounted(async () => {
  const fetchedKeys = await getSchedule(id, new Date())
  week.value = await getWeek(new Date())

  console.log(week.value)

  weekstr.value = week.value[1].toISOString().slice(0, 10) + " - " + week.value[6].toISOString().slice(0, 10)
  console.log(weekstr)
  schedule.value = fetchedKeys;

  console.log(schedule.value)
  console.log(isLoaded.value)

  for(let i = 1; i<week.value.length; i++){
    for(let l = 0; l<lessonArr.length; l++){
        scheduleArr.value[i][l] = schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00").length > 0 ? schedule.value.filter(s => s.sheduller == lessonArr[l] && s.date == week.value[i].toISOString().slice(0, 10)+"T00:00:00")[0] : {}
    }
  }
  console.log(week.value[0].toISOString().slice(0, 10)+"T00:00:00")
  console.log(week.value[1].toISOString().slice(0, 10)+"T00:00:00")
  console.log(scheduleArr.value)
  isLoaded.value = 1;
});
</script>

<template>
    <Navbar/>
    <div class="container ctr-start">
        <div class="btn-plate">
            <p>{{weekstr}}</p>
            <button @click="previousWeek">Предыдущая неделя</button><button class="btn-mg-l" @click="nextWeek">Следующая неделя</button>
        </div>
        <table v-if="isLoaded==1"> 
            <tr> 
                <td class="td-empty"></td>
                <td class="td-name">Понедельник</td>
                <td class="td-name">Вторник</td>
                <td class="td-name">Среда</td>
                <td class="td-name">Четверг</td>
                <td class="td-name">Пятница</td>
                <td class="td-name">Суббота</td>
            </tr>
            <tr> 
                <td class="td-time">8:45-10:20</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][0]).length != 0">
                    <div>{{ scheduleArr[1][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][0]).length != 0">
                    <div>{{ scheduleArr[2][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][0]).length != 0">
                    <div>{{ scheduleArr[3][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][0]).length != 0">
                    <div>{{ scheduleArr[4][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][0]).length != 0">
                    <div>{{ scheduleArr[5][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][0]).length != 0">
                    <div>{{ scheduleArr[6][0].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">10:35-12:10</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][1]).length != 0">
                    <div>{{ scheduleArr[1][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][1]).length != 0">
                    <div>{{ scheduleArr[2][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][1]).length != 0">
                    <div>{{ scheduleArr[3][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][1]).length != 0">
                    <div>{{ scheduleArr[4][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][1]).length != 0">
                    <div>{{ scheduleArr[5][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][1]).length != 0">
                    <div>{{ scheduleArr[6][1].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][1].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">12:25-14:00</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][2]).length != 0">
                    <div>{{ scheduleArr[1][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][2]).length != 0">
                    <div>{{ scheduleArr[2][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][2]).length != 0">
                    <div>{{ scheduleArr[3][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][2]).length != 0">
                    <div>{{ scheduleArr[4][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][2]).length != 0">
                    <div>{{ scheduleArr[5][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][2]).length != 0">
                    <div>{{ scheduleArr[6][2].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][2].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">14:45-16:20</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][3]).length != 0">
                    <div>{{ scheduleArr[1][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][3]).length != 0">
                    <div>{{ scheduleArr[2][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][3]).length != 0">
                    <div>{{ scheduleArr[3][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][3]).length != 0">
                    <div>{{ scheduleArr[4][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][3]).length != 0">
                    <div>{{ scheduleArr[5][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][3]).length != 0">
                    <div>{{ scheduleArr[6][3].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][3].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">16:35-18:10</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][4]).length != 0">
                    <div>{{ scheduleArr[1][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][4]).length != 0">
                    <div>{{ scheduleArr[2][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][4]).length != 0">
                    <div>{{ scheduleArr[3][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][4]).length != 0">
                    <div>{{ scheduleArr[4][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][4]).length != 0">
                    <div>{{ scheduleArr[5][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][4]).length != 0">
                    <div>{{ scheduleArr[6][4].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][4].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">18:25-20:00</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][5]).length != 0">
                    <div>{{ scheduleArr[1][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][5]).length != 0">
                    <div>{{ scheduleArr[2][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][5]).length != 0">
                    <div>{{ scheduleArr[3][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][5]).length != 0">
                    <div>{{ scheduleArr[4][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][5]).length != 0">
                    <div>{{ scheduleArr[5][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][5]).length != 0">
                    <div>{{ scheduleArr[6][5].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][5].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
            <tr> 
                <td class="td-time">20:15-21:50</td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[1][6]).length != 0">
                    <div>{{ scheduleArr[1][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[1][6].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[2][6]).length != 0">
                    <div>{{ scheduleArr[2][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[2][6].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[3][6]).length != 0">
                    <div>{{ scheduleArr[3][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[3][6].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[4][6]).length != 0">
                    <div>{{ scheduleArr[4][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[4][0].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[5][6]).length != 0">
                    <div>{{ scheduleArr[5][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[5][6].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
                <td><div class="key-card fill" v-if="Object.keys(scheduleArr[6][6]).length != 0">
                    <div>{{ scheduleArr[6][6].userName }}</div>
                    <button @click="deleteCell(scheduleArr[6][6].appId)" class="btn-mg-t">Удалить</button>
                </div></td>
            </tr>
        </table>
    </div>
</template>

<style>
td{
    width: 200px; 
    height: 100px;
}

.td-time{
    width: 100px; 
    height: 100px;
}

.td-name{
    width: 200px; 
    height: 50px;
}

.btn-plate{
    padding-top: 3vh;
    margin-left: 100px;
}

.fill{
    width: 100%;
    height: 100%;
}

.td-empty{
    height: 50px;
    width: 100px; 
}

.ctr-start{
    align-items: start;
}

.btn-mg-t{
    margin-top: 5px;
}
</style>