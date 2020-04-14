<template>
  <div class="employees">
    <div v-if="shouldShowEmployees" id="employee-list">
      <div id="button-margin">
        <button id="addEmployeeForm" v-on:click="showAddEmployeeForm"> Add Employee </button>
      </div>
      <div v-for="employee in allEmployees" :key="employee.id">
        <EmployeeList v-bind:employee="employee" id="list-of-employees" :selectedEmployeeId="employee.id" @showMoreEmployeeInfo="showMoreEmployeeInfo"/>
      </div>
    </div>
    <div v-if="shouldShowAddEmployeeForm">
      <AddEmployeeForm @hideAddEmployeeForm="showEmployees"/>
    </div>
    <div v-if="shouldShowMoreEmployeeInfo">
      <EmployeeInfo v-bind:employeeId="selectedEmployeeId" @returnToList="showEmployees" @showEditEmployee="showEditEmployeeForm"/>
    </div>
    <div v-if="shouldShowEditEmployee">
      <EditEmployee v-bind:initial="selectedEmployee" @returnToInfo="showMoreEmployeeInfo" @returnToHome="showEmployees"/>
    </div>
  </div>
</template>

<script>
import EmployeeList from '@/components/EmployeeList.vue'
import AddEmployeeForm from '@/components/AddEmployeeForm.vue'
import EmployeeInfo from '@/components/EmployeeInfo.vue'
import EditEmployee from '@/components/EditEmployee.vue'

export default {
  data() {
    return {
      shouldShowEmployees: true,
      shouldShowAddEmployeeForm: false,
      shouldShowMoreEmployeeInfo: false,
      shouldShowEditEmployee: false,
      allEmployees: [],
      selectedEmployeeId: "",
      selectedEmployee: {}
    }
  },
  methods: {
    showAddEmployeeForm() {
      this.shouldShowEmployees = false;
      this.shouldShowAddEmployeeForm = true;
      this.shouldShowMoreEmployeeInfo = false;
      this.shouldShowEditEmployee = false;
    },

    showEmployees() {
      this.shouldShowEmployees = true;
      this.shouldShowAddEmployeeForm = false;
      this.shouldShowMoreEmployeeInfo = false;
      this.shouldShowEditEmployee = false;
      this.getEmployeeList();
    },

    showMoreEmployeeInfo(id) {
      this.shouldShowEmployees = false;
      this.shouldShowAddEmployeeForm = false;
      this.shouldShowMoreEmployeeInfo = true;
      this.shouldShowEditEmployee = false;
      this.selectedEmployeeId = id;
    },

    showEditEmployeeForm(anEmployee) {
      this.shouldShowEmployees = false;
      this.shouldShowAddEmployeeForm = false;
      this.shouldShowMoreEmployeeInfo = false;
      this.shouldShowEditEmployee = true;
      this.selectedEmployee = anEmployee;
    },
    
    getEmployeeList() {
      fetch('http://localhost:8080/employeeapp/employees',
        {
          method: 'GET',
          headers: {
            Accept: "application/json",
            "Content-Tpye": "application/json"
          }
        }
      )
      .then ( (response) => {return response.json()})
      .then ( (employeeData) => {
        this.allEmployees = employeeData;
      })
      .catch( (err) => {console.log(err)});
    }
  },
  components: {
    EmployeeList,
    AddEmployeeForm,
    EmployeeInfo,
    EditEmployee
  },
  created() {
    this.getEmployeeList();
  },
}
</script>

<style>
  #button-margin {
      margin: 0 0 0 8px;
  }

  #employee-list {
    margin: 8px 8px 8px 8px;
  }

  #list-of-employees {
    border: 2px solid black;
    padding: 8px 8px 8px 8px;
    margin: 8px 8px 8px 8px;
  }
</style>
