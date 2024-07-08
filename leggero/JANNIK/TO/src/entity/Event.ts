import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";

@Entity({ schema: "my" })
export class Event {

    @PrimaryGeneratedColumn()
    id: number;

    @Column('date')
    startDate: Date;

    @Column('date')
    endDate: Date;

    @Column('text')
    result: string;
}
