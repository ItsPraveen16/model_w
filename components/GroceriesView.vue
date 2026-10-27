
<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const API_URL = 'http://localhost:8080/groceries'

const groceries = ref([])
const loading = ref(false)
const errorMessage = ref('')

async function getGroceries() {
  loading.value = true
  errorMessage.value = ''

  try {
    const response = await axios.get(API_URL)
    groceries.value = response.data
  } catch (error) {
    console.error('Error fetching groceries:', error)
    errorMessage.value = 'Could not load groceries. Check the backend.'
  } finally {
    loading.value = false
  }
}

async function deleteGrocery(id) {
  const confirmed = window.confirm('Delete this grocery?')

  if (!confirmed) return

  try {
    await axios.delete(`${API_URL}/${id}`)
    await getGroceries()
  } catch (error) {
    console.error('Error deleting grocery:', error)
    alert('Failed to delete grocery.')
  }
}

function editGrocery(grocery) {
  console.log('Selected grocery:', grocery)
  alert('We will implement Edit next.')
}

onMounted(getGroceries)
</script>

<template>
  <h2>Grocery List</h2>

  <p>
    <router-link to="/add-grocery">
      Add New Grocery
    </router-link>
  </p>

  <p v-if="loading">Loading groceries...</p>

  <p v-else-if="errorMessage">
    {{ errorMessage }}
  </p>

  <p v-else-if="groceries.length === 0">
    No groceries found.
  </p>

  <table v-else border="1" cellpadding="8">
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
          <button @click="editGrocery(grocery)">
            Edit
          </button>

          <button @click="deleteGrocery(grocery.id)">
            Delete
          </button>
        </td>
      </tr>
    </tbody>
  </table>
</template>
