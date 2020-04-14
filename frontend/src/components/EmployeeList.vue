<template>
    <div>
        <div class="employee">
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
            <div id="space"><br></div>
            <div id="dates">
                Birthday: {{employee.birthDate}}<br>
                Date of hire: {{employee.hiredDate}}
            </div>
            <div id="emails">
                Contact email: {{employee.contactEmail}}<br>
                Company email: {{employee.companyEmail}}
            </div>
            <div id="address">
                {{employee.address.street}}<br>
                <div v-if="employee.address.suite != null">
                    {{employee.address.suite}}
                </div>
                {{employee.address.city}}, {{employee.address.region}} {{employee.address.postal}}, {{employee.address.country}}
            </div>
            <div id="buttons">
                <button v-on:click.prevent=""> More Info </button>
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
    }
}
</script>

<style>
    .employee {
        display: grid;
        grid-template-columns: 1fr 15fr 1fr 15fr 1fr;
        grid-template-areas: 
            ". header . emails ."
            "space space space space space"
            ". address . dates .";

    }

    #card-header {
        display: inline-block;
        grid-area: header;
    }

    #name {
        display: inline-block;
        text-align: left;
        font-weight: bold;
        grid-area: name;
    }

    #title {
        font-style: italic;
        grid-area: title;
    }

    #space {
        grid-area: space;
    }

    #dates {
        display: inline-block;
        grid-area: dates;
    }

    #emails {
        display: inline-block;
        grid-area: emails;
    }

    #address {
        display: inline-block;
        grid-area: address;
    }

    #buttons {
        grid-area: buttons;
    }
</style>