import CourseError from "../models/error/CourseError";
import StudentError from "../models/error/StudentError";
import UserError from "../models/error/UserError";

export default function resetAllError( target : string ) : void {
    switch( target ){
        case 'course':
            resetCourseError();
            break;
        case 'user' : 
            resetUserError();
            break;
        case 'student': 
            resetStudentError();
            break;
    }
}

function resetCourseError() : void {
    let resetObj = { hasError : false , msg : '' };

    for( let key of Object.keys(CourseError) ){
        let _key = key as keyof typeof CourseError;

        CourseError[_key] = resetObj;
    }

}

function resetUserError() : void {
    let resetObj = { hasError : false , msg : '' };

    for( let key of Object.keys(UserError) ){
        let _key = key as keyof typeof UserError;

        UserError[_key] = resetObj;
    }
}

function resetStudentError() : void {
    let resetObj = { hasError : false , msg : '' };

    for( let key of Object.keys(StudentError) ){
        let _key = key as keyof typeof StudentError;

        StudentError[_key] = resetObj;
    }
}