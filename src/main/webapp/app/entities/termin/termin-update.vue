<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="pkvApp.termin.home.createOrEditLabel" v-text="$t('pkvApp.termin.home.createOrEditLabel')">Create or edit a Termin</h2>
                <div>
                    <div class="form-group" v-if="termin.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="termin.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.termin.datum')" for="termin-datum">Datum</label>
                        <div class="d-flex">
                            <input id="termin-datum" type="datetime-local" class="form-control" name="datum" :class="{'valid': !$v.termin.datum.$invalid, 'invalid': $v.termin.datum.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.termin.datum.$model)"
                            @change="updateInstantField('datum', $event)"/>
                        </div>
                        <div v-if="$v.termin.datum.$anyDirty && $v.termin.datum.$invalid">
                            <small class="form-text text-danger" v-if="!$v.termin.datum.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.termin.datum.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('pkvApp.termin.notiz')" for="termin-notiz">Notiz</label>
                        <input type="text" class="form-control" name="notiz" id="termin-notiz"
                            :class="{'valid': !$v.termin.notiz.$invalid, 'invalid': $v.termin.notiz.$invalid }" v-model="$v.termin.notiz.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('pkvApp.termin.arzt')" for="termin-arzt">Arzt</label>
                        <select class="form-control" id="termin-arzt" name="arzt" v-model="termin.arzt" >
                            <option v-bind:value="null"></option>
                            <option v-bind:value="termin.arzt && arztOption.id === termin.arzt.id ? termin.arzt : arztOption" v-for="arztOption in arzts" :key="arztOption.id">{{arztOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.termin.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./termin-update.component.ts">
</script>
