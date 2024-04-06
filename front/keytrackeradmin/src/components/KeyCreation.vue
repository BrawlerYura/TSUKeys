<script setup>
import { ref, onMounted } from 'vue';
import routes from '../routes.json'
const keyCreationRoute = routes.host + routes.createKey
const token = localStorage.getItem('token');

let keyNumber = "";
let buildingNumber = "";
const emit = defineEmits(['reloadKeys'])

const count = ref(0)

const numberPattern = /^\d+$/

function createKey(){
    console.log(keyNumber)
    keyNumber=keyNumber.replace(/\s/g, '');
    if(numberPattern.test(keyNumber)!=true){
        count.value=1;
    }
    else{
        count.value=0;
    }

    fetch(keyCreationRoute+'?Number='+keyNumber + '&building=' + buildingNumber, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
        })
        .then(response => {
            if (!response.ok) {
                count.value=2;
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
</script>

<template>
    <div class="key-card">
        <input type="text" placeholder="Номер кабинета" v-model="keyNumber" class="input-card"/>
        <input type="text" placeholder="Номер корпуса" v-model="buildingNumber" class="input-card"/>
        <button @click="createKey" class="btn-pd-b">Создать</button>
        <div v-if="count == 1" class="warning">
            Номер неверный
        </div>
        <div v-if="count == 2" class="warning">
            Что-то пошло не так
        </div>
    </div>
</template>

<style>
.input-card{
    width: 10vw;
    margin-top: 10px;
}

.btn-pd-b{
    margin-bottom: 10px;
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