<template>
    <div>
        <div class="employee-min">
            <div id="card-header">
                <div id="name">
                    {{employee.firstName}} {{employee.lastName}}
                </div>
                <div id="title">
                    {{employee.role}}
                    <div v-if="employee.businessUnit != null">
                        {{employee.businessUnit}}
                    </div>
                    <div v-if="employee.assignedTo != null">
                        Assigned to: {{employee.assignedTo}}
                    </div>
                </div>
            </div>
            <div id="buttons">
                <button v-on:click.prevent="showMoreEmployeeInfo"> More Info </button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
        }
    },
    props: {
        employee: {
            id: "",
            firstName: "",
            lastName: "",
            address: {},
            contactEmail: "",
            companyEmail: "",
            birthDate: "",
            hiredDate: "",
            role: "",
            businessUnit: " ",
            assignedTo: " "
        },
    },
    methods: {
        showMoreEmployeeInfo() {
            this.$emit('showMoreEmployeeInfo', this.employee.id);
        }
    }
}
</script>

<style>
    .employee-min {
        margin: 8px 8px 8px 8px;
        padding: 8px 8px 8px 8px;
        display: grid;
        grid-template-columns: 1fr 10fr 1fr;
        grid-template-areas: 
            ". header .";

    }

    #card-header {
        display: inline-block;
        text-align: center;
        grid-area: header;
    }

    #name {
        display: inline-block;
        font-weight: bold;
        grid-area: name;
    }

    #title {
        font-style: italic;
        grid-area: title;
    }

    #buttons {
        grid-area: buttons;
    }

</style>