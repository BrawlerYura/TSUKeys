<script setup>
import Application from './Application.vue'
import Navbar from './Navbar.vue'
import { getApplications } from '../functions/getApplications'
import { ref, onMounted } from 'vue';
import ApplicationCreation from './ApplicationCreation.vue';

const applications = ref([]);
const isUser = ref(0)

onMounted(async () => {
  const fetchedKeys = await getApplications();
  applications.value = fetchedKeys;
  if(localStorage.getItem("userToken")!=''){
    isUser.value = 1
  }
});
</script>

<template>
    <div class="cock"><Navbar/></div>
    <div class="key-list">
        <Application v-for="item in applications" :ID="item.id" :name="item.appFromUser.email" :keyNumber="item.key.number" :keyID="item.key.id" :time="item.date" :type="item.isRepeat"/>
        <ApplicationCreation v-if="isUser==1"/>
    </div>
</template>

<style>

</style>