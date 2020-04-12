<template>
  <div class="about">
    <h1>This is an about page</h1>
    <ul>
      <li v-for="employee in allEmployees" v-bind:key="employee.id">
        {{employee.firstName}}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allEmployees: []
    }
  },
  created() {
    fetch('http://localhost:8080/employeeapp/employees')
    .then ( (response) => {return response.json()})
    .then ( (employeeData) => {this.allEmployees = employeeData;})
    .catch( (err) => {console.log(err)})
  }
}
</script>
