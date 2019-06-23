<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('pkvApp.termin.home.title')" id="termin-heading">Termins</span>
            <router-link :to="{name: 'TerminCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-termin">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('pkvApp.termin.home.createLabel')">
                    Create new Termin
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="table-responsive" v-if="termins">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('datum')"><span v-text="$t('pkvApp.termin.datum')">Datum</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('notiz')"><span v-text="$t('pkvApp.termin.notiz')">Notiz</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('arzt.id')"><span v-text="$t('pkvApp.termin.arzt')">Arzt</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="termin of orderBy(termins, propOrder, reverse === true ? 1 : -1)"
                    :key="termin.id">
                    <td>
                        <router-link :to="{name: 'TerminView', params: {terminId: termin.id}}">{{termin.id}}</router-link>
                    </td>
                    <td>{{termin.datum | formatDate}}</td>
                    <td>{{termin.notiz}}</td>
                    <td>
                        <div v-if="termin.arzt">
                            <router-link :to="{name: 'ArztView', params: {arztId: termin.arzt.id}}">{{termin.arzt.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group flex-btn-group-container">
                            <router-link :to="{name: 'TerminView', params: {terminId: termin.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TerminEdit', params: {terminId: termin.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(termin)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="pkvApp.termin.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-termin-heading" v-bind:title="$t('pkvApp.termin.delete.question')">Are you sure you want to delete this Termin?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-termin" v-text="$t('entity.action.delete')" v-on:click="removeTermin()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./termin.component.ts">
</script>
