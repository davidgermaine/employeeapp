<template>
    <div>
        <div v-for="employee in allEmployees" v-bind:key="employee.id" class="employee">
            <div id="name">
                {{employee.firstName}} {{employee.lastName}}
            </div>
            <div id="title">
                {{employee.role}} for {{employee.businessUnit}}<br>
                Assigned to: {{employee.assignedTo}}
            </div>
            <div id="info">
                Birthday: {{employee.birthDate}}<br>
                Date of hire: {{employee.hiredDate}}<br>
                Contact email: {{employee.contactEmail}}<br>
                Company email: {{employee.companyEmail}}
            </div>
            <div id="address">
                {{employee.address.street}}<br>
                {{employee.address.suite}}<br>
                {{employee.address.city}}, {{employee.address.region}} {{employee.address.postal}}, {{employee.address.country}}
            </div>
        </div>
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

<style>

</style>