
<script setup>
// ================================================
// PART 1: JAVASCRIPT + VUE
// ================================================

// ref() creates reactive data.
// When these values change, Vue updates the UI.
import { ref, onMounted } from 'vue'

// Axios sends HTTP requests to Spring Boot.
import axios from 'axios'

// Backend URL
const API_URL = 'http://localhost:8080/courses'

// Reactive variables
const courses = ref([])       // Stores all courses
const name = ref('')          // Course name input
const department = ref('')    // Department input
const fee = ref(0)             // Fee input
const editingId = ref(null)   // ID of course being edited
const searchText = ref('')    // Search input
const loading = ref(false)    // Loading indicator
const error = ref('')         // Error message

// ================================================
// GET: FETCH ALL COURSES
// ================================================
async function fetchCourses() {
  loading.value = true
  error.value = ''

  try {
    // GET request to Spring Boot
    const response = await axios.get(API_URL)

    // response.data contains the returned course array
    courses.value = response.data
  } catch (err) {
    error.value = 'Unable to fetch courses.'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// ================================================
// POST: ADD COURSE / PUT: UPDATE COURSE
// ================================================
async function saveCourse() {
  // Basic validation
  if (!name.value.trim() || !department.value.trim()) {
    error.value = 'Enter the course name and department.'
    return
  }

  // JavaScript object sent as JSON
  const course = {
    name: name.value,
    department: department.value,
    fee: Number(fee.value)
  }

  try {
    if (editingId.value !== null) {
      // PUT: update an existing course
      await axios.put(
        `${API_URL}/${editingId.value}`,
        course
      )
    } else {
      // POST: create a new course
      await axios.post(API_URL, course)
    }

    resetForm()

    // Refresh the list after saving
    await fetchCourses()
  } catch (err) {
    error.value = 'Unable to save course.'
    console.error(err)
  }
}

// ================================================
// LOAD A COURSE INTO THE FORM FOR EDITING
// ================================================
function editCourse(course) {
  editingId.value = course.id
  name.value = course.name
  department.value = course.department
  fee.value = course.fee
}

// ================================================
// DELETE A COURSE
// ================================================
async function deleteCourse(id) {
  // confirm() displays a browser confirmation dialog
  if (!confirm('Are you sure you want to delete?')) {
    return
  }

  try {
    await axios.delete(`${API_URL}/${id}`)
    await fetchCourses()
  } catch (err) {
    error.value = 'Unable to delete course.'
    console.error(err)
  }
}

// ================================================
// RESET THE FORM
// ================================================
function resetForm() {
  name.value = ''
  department.value = ''
  fee.value = 0
  editingId.value = null
  error.value = ''
}

// ================================================
// FILTER COURSES USING JAVASCRIPT
// ================================================
function filteredCourses() {
  // Convert search text to lowercase
  const search = searchText.value.toLowerCase()

  // filter() creates a new array of matching courses
  return courses.value.filter(course =>
    course.name.toLowerCase().includes(search) ||
    course.department.toLowerCase().includes(search)
  )
}

// ================================================
// LIFECYCLE HOOK
// ================================================

// Fetch courses automatically when the page loads
onMounted(fetchCourses)
</script>

<template>
  <!-- ============================================
       PART 2: HTML + VUE TEMPLATE SYNTAX
       ============================================ -->

  <main class="container">

    <!-- Interpolation: {{ }} displays a variable -->
    <h1>Course Management System</h1>
    <p class="subtitle">Vue.js + Spring Boot + MongoDB</p>

    <!-- FORM: @submit.prevent stops page reload -->
    <form class="course-form" @submit.prevent="saveCourse">

      <!-- Conditional rendering with v-if -->
      <h2>{{ editingId !== null ? 'Edit Course' : 'Add Course' }}</h2>

      <label for="name">Course Name</label>

      <!-- v-model connects input to reactive data -->
      <input
        id="name"
        v-model="name"
        type="text"
        placeholder="Enter course name"
        required
      />

      <label for="department">Department</label>

      <input
        id="department"
        v-model="department"
        type="text"
        placeholder="Enter department"
        required
      />

      <label for="fee">Fee</label>

      <!-- .number converts the input to a number -->
      <input
        id="fee"
        v-model.number="fee"
        type="number"
        min="0"
        required
      />

      <!-- @click handles a click event -->
      <button type="submit">
        {{ editingId !== null ? 'Update Course' : 'Add Course' }}
      </button>

      <!-- v-if displays Cancel only when editing -->
      <button
        v-if="editingId !== null"
        type="button"
        class="cancel"
        @click="resetForm"
      >
        Cancel
      </button>
    </form>

    <!-- Display error only when error is not empty -->
    <p v-if="error" class="error">{{ error }}</p>

    <!-- Search box -->
    <input
      v-model="searchText"
      class="search"
      type="text"
      placeholder="Search courses or departments"
    />

    <!-- Loading indicator -->
    <p v-if="loading">Loading courses...</p>

    <!-- Empty-state message -->
    <p v-else-if="filteredCourses().length === 0">
      No courses found.
    </p>

    <!-- v-for repeats the card for each course -->
    <section
      v-else
      v-for="course in filteredCourses()"
      :key="course.id"
      class="course-card"
    >
      <h2>{{ course.name }}</h2>

      <p><strong>Department:</strong> {{ course.department }}</p>
      <p><strong>Fee:</strong> ₹{{ course.fee }}</p>

      <div class="actions">

        <!-- Pass the current course to the function -->
        <button type="button" @click="editCourse(course)">
          Edit
        </button>

        <!-- Pass its ID to the delete function -->
        <button
          type="button"
          class="delete"
          @click="deleteCourse(course.id)"
        >
          Delete
        </button>
      </div>
    </section>

  </main>
</template>

<style scoped>
/* ================================================
   PART 3: CSS STYLING
   ================================================ */

/* Main page container */
.container {
  max-width: 850px;          /* Maximum width */
  margin: 40px auto;         /* Center horizontally */
  padding: 24px;              /* Inner spacing */
  font-family: Arial, sans-serif;
  color: #1e293b;
}

/* Main heading */
h1 {
  text-align: center;
  color: #2563eb;
}

/* Subtitle */
.subtitle {
  text-align: center;
  color: #64748b;
  margin-bottom: 24px;
}

/* Form styling */
.course-form {
  display: flex;              /* Enable Flexbox */
  flex-direction: column;    /* Stack elements vertically */
  gap: 12px;                  /* Space between elements */
  padding: 24px;
  background: #f8fafc;
  border: 1px solid #cbd5e1;
  border-radius: 12px;
  margin-bottom: 24px;
}

/* Text boxes */
input {
  width: 100%;
  box-sizing: border-box;     /* Include padding in width */
  padding: 12px;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  font-size: 15px;
}

/* Style input when selected */
input:focus {
  outline: 2px solid #93c5fd;
  border-color: #2563eb;
}

/* Search box spacing */
.search {
  margin-bottom: 16px;
}

/* Common button styling */
button {
  padding: 11px 16px;
  border: none;
  border-radius: 6px;
  background: #2563eb;
  color: white;
  cursor: pointer;
}

/* Change button color on hover */
button:hover {
  background: #1d4ed8;
}

/* Cancel button */
.cancel {
  background: #64748b;
}

/* Each course card */
.course-card {
  padding: 20px;
  margin-bottom: 16px;
  border: 1px solid #cbd5e1;
  border-radius: 10px;
  background: #ffffff;
  box-shadow: 0 2px 5px #0000000d;
}

/* Course title */
.course-card h2 {
  margin-top: 0;
  color: #1d4ed8;
}

/* Arrange action buttons horizontally */
.actions {
  display: flex;
  gap: 10px;
  margin-top: 16px;
}

/* Delete button */
.delete {
  background: #dc2626;
}

.delete:hover {
  background: #b91c1c;
}

/* Error message */
.error {
  padding: 12px;
  color: #b91c1c;
  background: #fee2e2;
  border-radius: 6px;
}

/* Responsive design for smaller screens */
@media (max-width: 600px) {
  .container {
    margin: 12px auto;
    padding: 14px;
  }

  .actions {
    flex-wrap: wrap;
  }
}
</style>
