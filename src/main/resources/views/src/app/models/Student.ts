import { Course } from "./Course";

export class Student {
    id! : string;
    name! : string;
    dob! : string;
    gender! : number;
    education! : number;
    attendCourses! : Course[];
    // course! : string;
}