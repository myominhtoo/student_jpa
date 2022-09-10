import { Component } from '@angular/core';
import { User } from 'src/app/models/User';
import { Form } from '@angular/forms';

@Component({
    selector : 'add-user',
    templateUrl  : './add-user.component.html',
})
export class AddUserComponent {

    user : User = {
        id : '',
        name : '',
        email : '',
        password : '',
        confirm : '',
        role : 0,
    }

    handleAddUser( form : Form ) : void {

    }

}