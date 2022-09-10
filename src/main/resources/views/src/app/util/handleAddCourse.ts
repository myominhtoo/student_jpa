import { Course } from "../models/Course";

export default function handleAddCourse( payload : { attendCourses : Course[] , target : string } ) : void {

    let shouldDo = true;
    let idx = 0;

    let resultCourses : Course[] = payload.attendCourses;

    for( let course of payload.attendCourses ){ 
        if( course.id == payload.target ){
            shouldDo = false;
            break;
        }
        idx++;
    }


    if( shouldDo )  resultCourses.push({ id : payload.target , name : '' });
    else resultCourses.splice( idx , 1 );

    payload.attendCourses = resultCourses ;//reset value to real atteneCourses

}