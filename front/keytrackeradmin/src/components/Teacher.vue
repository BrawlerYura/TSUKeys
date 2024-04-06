<script setup>
const props = defineProps(['ID','name','job','email'])
</script>

<template>
    <div class="key-card" v-if="!isDeleted">
        <div>Имя: <a>{{ name }}</a></div>
        <div>Email: {{ email }}</div>
        <div>Желаемый статус: {{ job == 'Teacher' ? 'Преподаватель' : 'Студент'}}</div>
        <div v-if="!continuer" class="btn-mg-b"> 
            <button @click="confirm(true)">Одобрить</button>
            <button class="btn-mg-l" @click="confirm(false)">Отклонить</button>
        </div>
        <div v-else class="btn-mg-b"> 
            <div>Подтвердите действие</div>
            <button @click="changeRole()">Подтвердить</button>
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
    methods:{
        confirm(approve){
            this.approval=approve;
            this.continuer=true; 
            console.log(this.continuer)
        },
        changeRole(){
            let statusRoute = routes.host + routes.acceptTeacher + '?AppId=' + this.ID + '&IsAccept=' + this.approval
            const token = localStorage.getItem('token');

            fetch(statusRoute, {
                method: 'POST', 
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

.btn-mg-b{
  padding-bottom: 10px;
}
</style>