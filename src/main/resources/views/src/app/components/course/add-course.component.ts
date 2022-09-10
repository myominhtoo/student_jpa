import { Component } from '@angular/core';
import { Form } from '@angular/forms';
import { Course } from 'src/app/models/Course';

@Component({
    selector : 'add-course',
    templateUrl : './add-course.component.html',
})
export class AddCourseComponent {

    course : Course = {
        id : '',
        name : ''
    };

    handleAddCourse( form : Form ) : void {
        
    }

}