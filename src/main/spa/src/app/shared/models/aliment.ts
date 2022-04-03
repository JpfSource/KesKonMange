import {Allergie} from './allergie';
import {Score} from './score';

export class Aliment {

    id!: number;
    ean!: string;
    libelle!: string;
    alimentAllergies!: Allergie[];
    alimentScores!: Score[];
    imgUrl?: string;
    energyKcal100g!: number;
    motsCles?: string;
    dateMaj!: Date;
}    