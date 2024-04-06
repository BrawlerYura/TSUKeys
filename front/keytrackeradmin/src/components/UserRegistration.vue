<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import routes from '../routes.json'

const router = useRouter()

let loginstr = ""
let password=""
let facNumber = ''

let name1 = ''
let name2 = ''
let name3 = ''

const bdate = ref("")

let deanId = ""

const count = ref(0)
const deans = ref([])

const emailPattern = /[\w-\.]+@([\w-]+\.)+[\w-]{2,4}/;

watch(bdate, async (newQuestion, oldQuestion) => {
    if(newQuestion > "2023-01-01"){
        count.value = 10
    }
    else{
        count.value = 0
    }
})


function changeRole(token){
    fetch(routes.host+routes.changeRole+'?desiredRole=Teacher&deanId='+deanId, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }

        })
        .then(response => {
            if (!response.ok) {
                count.value=6;
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);

            if(data.token==""){
                count.value=4;
            }
            else{
                count.value=5;
            }
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

function register(){
    if(count.value == 10){
        return;
    }

    console.log('register')

    console.log(emailPattern.test(loginstr))
    loginstr=loginstr.replace(/\s/g, '');
    password=password.replace(/\s/g, '');

    if(emailPattern.test(loginstr)!=true){
        count.value=1
    }
    else{
        count.value=0
    }

    if(password.length<8){
        count.value=2
    }
    else if(password.length>20){
        count.value=3
    }
    else{
        count.value=0
    }

    let registrationRoute = routes.host + routes.userRegistration
    fetch(registrationRoute, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: loginstr,
                password: password,
                name: name1+" "+name2+" "+name3,
                birthDate: bdate.value
            })
        })
        .then(response => {
            console.log(name1+" "+name2+" "+name3)
            if (!response.ok) {
                count.value=6;
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);

            changeRole(data.token)
            router.push('/')
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

onMounted(async () => {
    try {
        const response = await fetch(routes.host+routes.deans, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log(data)
        deans.value = data
        deans.value = deans.value.filter(d => d.isActive == true)
        console.log(deans.value)
    } catch (error) {
        console.error('There was a problem with your fetch operation:', error);
    }
});
</script>

<template>
    <div class="center">
        <div class="container">
            <h1>Регистрация</h1>
            <div class=".input-container">
                <input type="text" placeholder="Фамилия" v-model="name1"/>
                <input type="text" placeholder="Имя"v-model="name2"/>
                <input type="text" placeholder="Отчество"v-model="name3"/>
                <input type="date" placeholder="Дата рождения" v-model="bdate"/>
                <input type="text" placeholder="Email" v-model="loginstr"/>
                <input type="password" placeholder="Пароль" v-model="password"/>
                <select v-model="deanId" class="date-xd">
                    <option v-for="dean in deans" :key="dean.id" :value="dean.id">{{ dean.name }}</option>
                </select>
            </div>
            <div>
                <button @click="this.$router.push('/login')">Войти</button>
                <button class="btn-mg-l" @click="register()">Зарегистрироваться</button>
                <button class="btn-mg-l" @click="this.$router.push('/registration')">Зарегистрировать деканат</button>
            </div>
            <div v-if="count == 1" class="warning">
                Email неверный
            </div>
            <div v-if="count == 2" class="warning">
                Пароль должен состоять как минимум из 8 символов
            </div>
            <div v-if="count == 3" class="warning">
                Пароль должен состоять максимум из 20 символов
            </div>
            <div v-if="count == 4" class="warning">
                Email уже используется
            </div>
            <div v-if="count == 5" class="warning">
                Аккаунт зарегистрирован. Дождитесь активации администратором
            </div>
            <div v-if="count == 6" class="warning">
                Неверные данные
            </div>
            <div v-if="count == 10" class="warning">
                Неверная дата
            </div>
        </div>
    </div>
</template>

<style>
.warning{
    margin: 10px;
    padding-left: 5px;
    padding-right: 5px;
    background-color: #faa405;
    color: black;
    border-radius:  10px;
}

.date-xd{
    width: 300px;
    margin-bottom: 8px;
}

.btn-mg-b-8{
    margin-bottom: 8px;
}
</style>