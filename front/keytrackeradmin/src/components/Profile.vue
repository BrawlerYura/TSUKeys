<script setup>
import Navbar from './Navbar.vue'
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue'
import routes from '../routes.json'
import { getProfile } from '../functions/getProfile';

const router = useRouter()

const loginstr = ref("")
const password = ref("")
const name = ref([])
const name1 = ref("")
const name2 = ref("")
const name3 = ref("")
const bdate = ref("")

const count = ref(0)

function setProfile(){
    console.log(password.value)
    let registrationRoute = routes.host + routes.profile
    let userToken = localStorage.getItem("userToken")
    fetch(registrationRoute, {
            method: 'PUT', 
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + userToken
            },
            body: JSON.stringify({
                email: loginstr.value,
                password: password.value == null ? "" : password.value,
                name: name1.value+" "+name2.value+" "+name3.value,
                birthDate: bdate.value
            })
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
        })
        .catch(error => {
            console.error('There was a problem with your fetch operation:', error);
        });
}

onMounted(async () => {
    const fetchedKeys = await getProfile();
    name.value = fetchedKeys.name.split(" ");
    name1.value = name.value[0]    
    name2.value = name.value[1] 
    name3.value = name.value[2] 
    console.log(name)
    loginstr.value = fetchedKeys.email;
    password.value = fetchedKeys.password;
    bdate.value = fetchedKeys.birthDate.slice(0, 10)
    console.log(bdate.value)
});
</script>

<template>
    <Navbar/>
    <div class="center">
        <div class="container">
            <h1>Профиль</h1>
            <div class=".input-container">
                <input type="text" placeholder="Фамилия" v-model="name1"/>
                <input type="text" placeholder="Имя"v-model="name2"/>
                <input type="text" placeholder="Отчество"v-model="name3"/>
                <input type="text" placeholder="Логин" v-model="loginstr"/>
                <input type="password" placeholder="Пароль" v-model="password"/>
                <input type="date" v-model="bdate"/>
            </div>
            <div>
                <button @click="setProfile()">Применить</button>
            </div>
            <div v-if="count == 1" class="warning">
                Неверная дата
            </div>
        </div>
    </div>
</template>