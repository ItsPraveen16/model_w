
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const API_URL = 'http://localhost:8080/groceries'

const name = ref('')
const category = ref('')
const price = ref('')
const quantity = ref('')
const errorMessage = ref('')

async function addGrocery() {
  errorMessage.value = ''

  const grocery = {
    name: name.value,
    category: category.value,
    price: Number(price.value),
    quantity: Number(quantity.value)
  }

  try {
    await axios.post(API_URL, grocery)

    alert('Grocery added successfully!')

    router.push('/groceries')
  } catch (error) {
    console.error('Error adding grocery:', error)
    errorMessage.value =
      error.response?.data?.message ||
      'Failed to add grocery. Check the backend.'
  }
}
</script>

<template>
  <h2>Add Grocery</h2>

  <p v-if="errorMessage">
    {{ errorMessage }}
  </p>

  <form @submit.prevent="addGrocery">
    <div>
      <label for="name">Name:</label>
      <input id="name" v-model="name" required />
    </div>

    <br />

    <div>
      <label for="category">Category:</label>
      <input id="category" v-model="category" required />
    </div>

    <br />

    <div>
      <label for="price">Price:</label>
      <input
        id="price"
        v-model="price"
        type="number"
        min="0"
        step="0.01"
        required
      />
    </div>

    <br />

    <div>
      <label for="quantity">Quantity:</label>
      <input
        id="quantity"
        v-model="quantity"
        type="number"
        min="0"
        required
      />
    </div>

    <br />

    <button type="submit">Add Grocery</button>
  </form>

  <p>
    <router-link to="/groceries">
      Back to Grocery List
    </router-link>
  </p>
</template>
