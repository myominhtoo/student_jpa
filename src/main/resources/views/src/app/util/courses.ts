import { Course } from "../models/Course";
import { Status } from "../models/Status";
import CourseService from "../services/course/CourseService";

export async function fetchCourses( coureService : CourseService , payload : { data : { courses : Course[]  } , status : Status } ) : Promise<void>{

    payload.status.isLoading = true;

    coureService.getCourses()
    .subscribe({
        next : ( datas ) => {
            payload.status.isLoading = false;
            
            if( datas.length == 0 ) payload.status.isBlank = true;
            else payload.status.isBlank = false;

            payload.data.courses = datas;

        },
        error : ( e ) => console.log( e )
    })

}

