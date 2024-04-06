<script setup>
import { ref, onMounted, watch } from 'vue';
import routes from '../routes.json'


const keys = ref([])
const selectedKey = ref('')
const token = localStorage.getItem('token');

const userToken = localStorage.getItem('userToken');
const keysRoute = routes.host + routes.keys;
const ldate = ref('')
let isRepeat = false;

const chosenLesson = ref("First")
const lessonArr = ["First", "Second", "Third", "Fourth","Fifth","Sixth","Seventh"]

const count = ref(0)

let now = new Date()

watch(ldate, async (newQuestion, oldQuestion) => {
    if(newQuestion < now.toISOString().slice(0, 10)){
        count.value = 1
    }
    else{
        count.value = 0
    }
    console.log(ldate.value)
})

async function createApp(){
    if(count.value == 1){
        return;
    }

    console.log(ldate.value)
    console.log(routes.host+routes.createApp+'?KeyId='+selectedKey.value+'&Schedule='+chosenLesson.value+'&Date='+ldate.value+'&isRepeat='+isRepeat)

    try {
        const response = await fetch(routes.host+routes.createApp+'?KeyId='+selectedKey.value+'&Schedule='+chosenLesson.value+'&Date='+ldate.value+'&isRepeat='+isRepeat, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + userToken
            }
        });

        if (!response.ok) {
            count.value = 2;
            throw new Error('Network response was not ok');
        }

        keys.value = await response.json();
        console.log(keys.value)
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
}

onMounted(async () => {
    try {
        const response = await fetch(keysRoute, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        keys.value = await response.json();
        console.log(keys.value)
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
});
</script>

<template>
<div class="key-card">
    <div>Создать заявку</div>
    <select v-model="selectedKey" class="date-xdd">
        <option v-for="key in keys" :value="key.id">{{ key.number }}</option>
    </select>
    <select v-model="chosenLesson" class="date-xdd">
        <option v-for="l in lessonArr" :value="l">{{ l }}</option>
    </select>
    <select v-model="isRepeat" class="date-xdd">
        <option :value="false">{{ 'Неповторяющаяся' }}</option>
        <option :value="true">{{ 'Повторяющаяся' }}</option>
    </select>
    <input type="date" class="date-xdd" v-model="ldate"/>
    <button @click="createApp()" class="btn-mg-b-8">Создать заявку</button>
    <div v-if="count == 1" class="warning">
        Неверная дата
    </div>
    <div v-if="count == 2" class="warning">
        Ячейка расписания уже занята
    </div>
    
</div>
</template>

<style>
.date-xdd{
    width: 250px;
    margin-bottom: 8px;
}

.btn-mg-b-8{
    margin-bottom: 8px;
}

.warning{
    margin: 10px;
    padding-left: 5px;
    padding-right: 5px;
    background-color: #faa405;
    color: black;
    border-radius:  10px;
}
</style>