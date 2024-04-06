<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import routes from '../routes.json'

const router = useRouter()

let loginstr = ""
let password=""
let name = ''
let facNumber = ''

const count = ref(0)

const emailPattern = /[\w-\.]+@([\w-]+\.)+[\w-]{2,4}/;



function register(){
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

    let registrationRoute = routes.host + routes.registration + '?Email=' + loginstr + '&Password=' + password + '&Name=' + name + '&FacultyNumber=' + facNumber

    fetch(registrationRoute, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json'
            },
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
</script>

<template>
    <div class="center">
        <div class="container">
            <h1>Регистрация</h1>
            <div class=".input-container">
                <input type="text" placeholder="Название" v-model="name"/>
                <input type="text" placeholder="Номер факультета" v-model="facNumber"/>
                <input type="text" placeholder="Логин" v-model="loginstr"/>
                <input type="password" placeholder="Пароль" v-model="password"/>
            </div>
            <div>
                <button @click="this.$router.push('/login')">Войти</button>
                <button class="btn-mg-l" @click="register">Зарегистрироваться</button>
                <button class="btn-mg-l" @click="this.$router.push('/reguser')">Зарегистрироваться как сотрудник</button>
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
</style>