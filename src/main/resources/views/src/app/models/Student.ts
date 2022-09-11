import { Course } from "./Course";

export class Student {
    id! : string;
    name! : string;
    dob! : string;
    gender! : number;
    phone! : string;
    education! : number;
    attendCourses! : Course[];
    // course! : string;
}