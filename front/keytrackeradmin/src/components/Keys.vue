<script setup>
import Key from './Key.vue'
import KeyCreation from './KeyCreation.vue'
import Navbar from './Navbar.vue'
import { getKeys } from '../functions/getKeys'
import { ref, onMounted } from 'vue';

const keys = ref([]);

async function reloadKeys(){
    const fetchedKeys = await getKeys();
    keys.value = fetchedKeys;
}

onMounted(async () => {
  const fetchedKeys = await getKeys();
  keys.value = fetchedKeys;
});

</script>

<template>
    <div class="cock"><Navbar/></div>
    <div class="key-list">
        <Key v-for="item in keys" :ID="item.id" :lastInPossession="item.currentUserId" :lastInPossessionName="item.currentUser" :keyNumber="item.number" :buildingNumber="item.building" :keyObj="item" @reloadKeys="console.log('reload')"/>
        <KeyCreation @reloadKeys="reloadKeys()"/>
    </div>
</template>

<script>
export default {
  methods: {
    methodThatForcesUpdate() {
      // ...
      this.$forceUpdate();  // Notice we have to use a $ here
      // ...
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