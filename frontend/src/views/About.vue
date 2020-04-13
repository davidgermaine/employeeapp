<template>
  <div class="employees">
    <div v-if="shouldShowEmployees">
      <button id="addEmployeeForm" v-on:click="showAddEmployeeForm"> Add Employee </button>
      <EmployeeInfo/>
    </div>
    <div v-if="shouldShowAddEmployeeForm">
      <AddEmployeeForm @hideAddEmployeeForm="showEmployeeForm"/>
    </div>
  </div>
</template>

<script>
import EmployeeInfo from '@/components/EmployeeInfo.vue'
import AddEmployeeForm from '@/components/AddEmployeeForm.vue'

export default {
  data() {
    return {
      shouldShowEmployees: true,
      shouldShowAddEmployeeForm: false,
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
    }
  },
  components: {
    EmployeeInfo,
    AddEmployeeForm
  }
}
</script>
