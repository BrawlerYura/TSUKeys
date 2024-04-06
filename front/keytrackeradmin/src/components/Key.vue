<script setup>
import routes from '../routes.json'
import { ref, onMounted } from 'vue';
import { getSchedule} from '../functions/getSchedule.js'
const props = defineProps(['ID','keyNumber','lastInPossessionName','lastInPossession','buildingNumber','keyObj'])

const xexe = ref(0)
const userList = ref([])
const chosenUser = ref({})
const currentUserName = ref("")

function handOver(){
    console.log(chosenUser.value)
    const token = localStorage.getItem('token');
            fetch(routes.host+routes.handOver+'?id='+props.ID+'&userId='+chosenUser.value.userId, {
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                else{
                    xexe.value = 3
                }
                return response.json();
            })
            .then(data => {
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
            console.log('bebra')
}

function returnKey(){
    const token = localStorage.getItem('token');
            fetch(routes.host+routes.returnKey+'?id='+props.ID, {
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                else{
                    xexe.value = 3
                }
                return response.json();
            })
            .then(data => {
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
            console.log('bebra')
}

onMounted(async () => {
    let now = new Date()
    let schedule = await getSchedule(props.ID, now)
    console.log(schedule)
    userList.value = schedule.filter(s => s.date.slice(0, 10) == now.toISOString().slice(0, 10))
    console.log(userList.value)
    if(props.keyObj.isReturned == true && schedule.filter(s => s.date.slice(0, 10) == now.toISOString().slice(0, 10)).length > 0){
        xexe.value = 1
    }
    else if(props.keyObj.isReturned == false){
        xexe.value = 2
        currentUserName.value = schedule.filter(s => s.userId == props.lastInPossession)[0].userName
        console.log(currentUserName.value, "xdddddddddddddddddddddd")
    }
    else if(props.keyObj.isReturned == null){
        xexe.value = 3
        currentUserName.value = schedule.filter(s => s.userId == props.lastInPossession)[0].userName
        console.log(currentUserName.value, "xdddddddddddddddddddddd")
    }
});
</script>

<template>
    <div class="key-card" v-if="!isDeleted">
        <div>Кабинет: <router-link :to="'/schedule/'+ID">{{ keyNumber }}</router-link></div>
        <div>Корпус: {{ buildingNumber }}</div>
        <div>Владеет: <a>{{ currentUserName}}</a></div>
        <button v-if="!continuer" class="btn-pd-b" @click="confirm()">Удалить</button>
        <div v-else class="btn-mg-b"> 
            <div>Подтвердите действие</div>
            <button @click="deleteKey()">Подтвердить</button>
            <button class="btn-mg-l" @click="continuer=false;">Отменить</button>
        </div>
        <div v-if="xexe==1" class="btn-pd-b">
            <select v-model="chosenUser">
                <option v-for="l in userList" :value="l">{{ l.userName }}</option>
            </select>
            <button class="btn-pd-b btn-mg-l" @click="handOver()">Отдать</button>
        </div>
        <button v-if="xexe==2" class="btn-pd-b" @click="returnKey()">Вернуть</button>
        <div v-if="xexe==3"></div>
    </div>
</template>

<script>
import routes from '../routes.json'
const deleteKeyRoute = routes.host + routes.deleteKey

export default {
    data(){
        return {
            continuer: false,
            isDeleted: false
        }
    },
    methods:{
        confirm(){
            this.continuer=true; 
            console.log(this.continuer)
        },
        deleteKey(){
            const token = localStorage.getItem('token');
            fetch(deleteKeyRoute+'?id='+this.ID, {
                method: 'DELETE', 
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                else{
                    this.isDeleted = true;
                }
                return response.json();
            })
            .then(data => {
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
            this.$emit('reloadKeys')
            console.log('bebra')
        },
        handOver(){

        },
        return(){

        }
    }
}
</script>

<style>
.btn-pd-b{
    margin-bottom: 5px;
}
</style>