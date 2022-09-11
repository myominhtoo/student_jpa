import CourseError from "src/app/models/error/CourseError";
import StudentError from "src/app/models/error/StudentError";
import UserError from "src/app/models/error/UserError";

export default function isAllOk( target : string) : boolean {
    
    let isAllOk = true;

    switch( target ){
        case 'user' : 
            isAllOk = isAllOk && checkForUser();
            break;
        case 'course' : 
            isAllOk = isAllOk && checkForCourse();
            break;
        case 'student' : 
            isAllOk = isAllOk && checkForStudent();
    }

    return isAllOk;

}

function checkForStudent() : boolean {

    let status = true;

    for( let key of Object.keys(StudentError) ){
       let _key = key as keyof typeof StudentError;

       status = status && !StudentError[_key].hasError;
    }
console.log(StudentError)
    return status;

}

function checkForCourse() : boolean {
    let status = true;

    for( let key of Object.keys(CourseError) ){
        let _key = key as keyof typeof CourseError;

        status = status && !CourseError[_key].hasError;
    }

    return status;
}

function checkForUser() : boolean {
    let status = true;

    for( let key of Object.keys(UserError) ){
        let _key = key as keyof typeof UserError;

        status = status && !UserError[_key].hasError;
    }

    return status;
}

