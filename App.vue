<script setup>
import {ref,onMounted} from 'vue'
import axios from 'axios'

const API_URL = 'http://localhost:8080/groceries'
const groceries = ref([])
const name = ref("")
const category = ref("")
const price = ref()
const quantity = ref()
const editingId = ref(null)

async function getGroceries() {
  const res = await axios.get(API_URL)
  groceries.value = res.data
}

async function addGroceries() {
  const grocery = {
    name : name.value,
    category : category.value,
    price : Number(price.value),
    quantity : Number(quantity.value)
  }
  if(editingId.value ===null)
  {
    await axios.post(API_URL,grocery)
  }
  else
  {
    await axios.put(API_URL+'/'+editingId.value,grocery)
  }
  clearForm()
  await getGroceries()
}


async function editing(grocery) {
  editingId.value=grocery.id;
  name.value=grocery.name;
  category.value=grocery.category;
  price.value=grocery.price;
  quantity.value=grocery.quantity;
}

async function deleteGrocery(id) {
  await aixos.delete(API_URL+'/'+id)
  getGroceries()
}

function clearForm() {
  editingId.value = null
  name.value = ''
  category.value = ''
  price.value = ''
  quantity.value = ''
}
onMounted(getGroceries)
</script>
<template>
<h1>Groceries Managemnet</h1>
<h3>{{ editingId === null ? 'Add Grocery' : 'Update Grocery' }}</h3>
<form @submit.prevent="addGroceries">
  <input v-model="name" placeholder="Name" required>
  <input v-model="category" placeholder="Category" required>
  <input v-model="price" type="number" placeholder="Price" required :min="0">
  <input v-model="quantity" type="number" placeholder="Quantity" :min="0" required>
  <button type="submit">{{ editingId === null ? 'ADD': 'UPDATE' }}</button>
  <button type="button" @click="clearForm">Clear </button>
</form>

<h2>Grocery List</h2>

  <p v-if="groceries.length === 0">No groceries found.</p>

  <table v-else border="1">
    <thead>
      <tr>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Actions</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="grocery in groceries" :key="grocery.id">
        <td>{{ grocery.name }}</td>
        <td>{{ grocery.category }}</td>
        <td>{{ grocery.price }}</td>
        <td>{{ grocery.quantity }}</td>
        <td>
          <button @click="editing(grocery)">Edit</button>
          <button @click="deleteGrocery(grocery.id)">Delete</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>
<style scoped>
</style>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <h1>Grocery Management System</h1>

  <nav>
    <RouterLink to="/">Home</RouterLink> |
    <RouterLink to="/groceries">Groceries</RouterLink> |
    <RouterLink to="/add-grocery">Add Grocery</RouterLink>
  </nav>

  <hr />

  <RouterView />
</template>

