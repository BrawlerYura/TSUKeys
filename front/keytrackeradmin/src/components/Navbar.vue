<script setup>
import { useRouter } from 'vue-router'
import { ref, onMounted } from 'vue';

const router = useRouter()
const isDean = ref(0)

function logout(){
    localStorage.removeItem('token')
    localStorage.removeItem('userToken')

    router.push('/')
}

onMounted(async () => {
    if(localStorage.getItem("userToken")!=""){
        isDean.value = 1
    }
});
</script>

<template>
    <div class="top-line">
        <div class="navbar">
            <div class="left-links">
                <router-link to="/keys">Ключи</router-link>
                <router-link to="/applications">Заявки</router-link>
                <router-link to="/teachers">Пользователи</router-link>
                <router-link to="/Workers">Работники</router-link>
            </div>
            <div class="right-links">
                <button @click="router.push('/profile')" v-if="isDean==1">Профиль</button>
                <button @click="logout" class="btn-mg-l">Выход</button>
            </div>
        </div>
    </div>
</template>

<style>
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: white;
    padding: 5px;
    width: 60%;
}

.top-line{
    background-color: #333333;
    position: fixed;
    width: 100%;
    left: 0;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 5vh;
}

a {
    margin-right: 10px;
}

@media (prefers-color-scheme: light) {
    .top-line{
        background-color: #f5f0f0;
    }
}
</style>