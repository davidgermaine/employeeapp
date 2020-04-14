<template>
  <div class="employees">
    <div v-if="shouldShowEmployees" id="employee-list">
      <button id="addEmployeeForm" v-on:click="showAddEmployeeForm"> Add Employee </button>
      <div v-for="employee in allEmployees" :key="employee.id" :selectedEmployee="employee">
        <EmployeeList v-bind:employee="employee" id="list-of-employees"/>
      </div>
    </div>
    <div v-if="shouldShowAddEmployeeForm">
      <AddEmployeeForm @hideAddEmployeeForm="showEmployeeForm"/>
    </div>
  </div>
</template>

<script>
import EmployeeList from '@/components/EmployeeList.vue'
import AddEmployeeForm from '@/components/AddEmployeeForm.vue'

export default {
  data() {
    return {
      shouldShowEmployees: true,
      shouldShowAddEmployeeForm: false,
      allEmployees: [],
      selectedEmployee: {}
    }
  },
  methods: {
    showAddEmployeeForm() {
      this.shouldShowEmployees = false;
      this.shouldShowAddEmployeeForm = true;
    },

    showEmployeeForm() {
      this.shouldShowEmployees = true;
      this.shouldShowAddEmployeeForm = false;
      this.getEmployeeList();
    },
    
    getEmployeeList() {
      fetch('http://localhost:8080/employeeapp/employees')
      .then ( (response) => {return response.json()})
      .then ( (employeeData) => {
        this.allEmployees = employeeData;
      })
      .catch( (err) => {console.log(err)});
    }
  },
  components: {
    EmployeeList,
    AddEmployeeForm
  },
  created() {
    this.getEmployeeList();
  },
}
</script>

<style scoped>
  #employee-list {
    margin: 8px 8px 8px 8px;
  }

  #list-of-employees {
    border: 2px solid black;
    padding: 8px 8px 8px 8px;
    margin: 8px 8px 8px 8px;
  }
</style>
