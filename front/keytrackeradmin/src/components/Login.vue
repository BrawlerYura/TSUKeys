<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import routes from '../routes.json'

const router = useRouter()

let loginstr = ""
let password = ""

const count = ref(0)

function login(){
    let loginRoute = routes.host + routes.login
    console.log(loginstr,password)
    
    fetch(loginRoute, {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: loginstr,
                password: password
            })
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

            if(data.token==""){
                count.value=1;
            }
            else{
                localStorage.setItem('token', data.token);
                localStorage.setItem('userToken', data.userToken);
                router.push("/keys")
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
            <h1>Вход</h1>
            <div class=".input-container">
                <input type="text" placeholder="Логин" v-model="loginstr"/>
                <input type="password" placeholder="Пароль" v-model="password"/>
            </div>
            <div>
                <button @click="login">Войти</button>
                <button class="btn-mg-l" @click="this.$router.push('/registration')">Зарегистрироваться</button>
            </div>
            <div v-if="count == 1" class="warning">
                Ваш аккаунт еще не активирован администратором
            </div>
            <div v-else-if="count == 2" class="warning">
                Неправильный логин или пароль
            </div>
            <router-link to="/adminLogin">Панель администратора</router-link>
        </div>
    </div>
</template>

<script>

</script>

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