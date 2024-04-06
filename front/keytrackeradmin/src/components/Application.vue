<template>
    <div class="key-card" v-if="!isDeleted">
        <div>Кабинет: <router-link :to="'/schedule/'+keyID">{{ keyNumber }}</router-link></div>
        <div>Имя: <a>{{ name }}</a></div>
        <div>Время: {{ time }}</div>
        <div>Тип: {{ type ? 'повторяющаяся' : 'обычная' }}</div>
        <div v-if="!continuer" class="btn-mg-b"> 
            <button @click="confirm(true)">Одобрить</button>
            <button class="btn-mg-l" @click="confirm(false)">Отклонить</button>
        </div>
        <div v-else class="btn-mg-b"> 
            <div>Подтвердите действие</div>
            <button @click="changeStatus()">Подтвердить</button>
            <button class="btn-mg-l" @click="continuer=false;">Отменить</button>
        </div>
    </div>
</template>

<script>
import routes from '../routes.json'

export default {
    data(){
        return {
            continuer: false,
            approval: false,
            isDeleted: false
        }
    },
    props: {
        ID: {
            type: String,
            required: false
        },
        keyNumber:{
            type: String,
            required: false
        },
        name:{
            type: String,
            required: false
        },
        time:{
            type: String,
            required: false
        },
        type:{
            type: String,
            required: false
        },
        keyID:{
            type: String,
            required: false
        }
    },
    methods:{
        confirm(approve){
            this.approval=approve;
            this.continuer=true; 
            console.log(this.continuer)
        },
        changeStatus(){
            let statusRoute = routes.host + routes.confirmApplication + '?id=' + this.ID + '&IsConfirm=' + this.approval
            const token = localStorage.getItem('token');

            fetch(statusRoute, {
                method: 'PUT', 
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                this.isDeleted = true
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
        }
    }
}
</script>

<style>
.key-list{
    display: flex;
    flex-direction: column; 
    justify-content: left;
    max-width: 15vw;
}
</style>