// noinspection HtmlUnknownTarget

import './WelocomePage.scss';
import React from 'react';
import { IonButton, IonButtons, IonContent, IonHeader, IonText, IonTitle, IonToolbar } from '@ionic/react';

const WelcomePage: React.FC<{t: string}> = ({t}) => {
    return (
        <>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>{t}</IonTitle>
                    <IonButtons className="ion-padding" slot="end">
                        <IonButton routerLink="/login" color="primary">
                            Login
                        </IonButton>
                        <IonButton routerLink="/register" color="primary">
                            Register
                        </IonButton>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>

            <IonContent scrollY={true} className="ion-padding">
                <div className="container">
                    <div className="row mb-5 d-flex justify-content-center">
                        <div className="banner d-flex flex-column justify-content-center align-items-center">
                            <div className="banner-content">
                                <h1 className="text-white">
                                    Witaj w wyszukiwarce współlokatorów
                                </h1>
                            </div>
                        </div>
                    </div>

                    <div className="row my-5">
                        <div className="col-10 col-md-6 justify-content-center align-items-center">
                            <div>
                                <img src="assets/anywhere.jpg" alt="world map" className="img-fluid"/>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="d-flex flex-column justify-content-center h-100">
                                <div className="mt-4 mt-lg-0">
                                    <h2>Praktycznie i wygodnie</h2>
                                </div>
                                <div>
                                    <div>
                                        <IonText color="primary" className="ion-padding">
                                            <h4>Znajdź najlepszy pokój i współlokatora</h4>
                                        </IonText>
                                        <div>
                                            Przejrzyj ogłoszenia od osób zainteresowanych wynajmem pokoi z całej Polski.
                                            Gdy wybierzesz któreś z nich, ułatwimy Ci kontakt z osobą zainteresowaną.
                                        </div>
                                    </div>
                                    <div>
                                        <IonText color="primary" className="ion-padding">
                                            <h4>Przejrzysty opis ofert</h4>
                                        </IonText>
                                        <div>
                                            Dzięki ustandaryzowanemu opisowi w prosty sposób znajdź
                                            najlepszą oferte dla siebie i swojego portfela.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="row my-5">
                        <div className="col-lg-6">
                            <div className="d-flex flex-column justify-content-center h-100">
                                <div className="mt-4 mt-lg-0">
                                    <h2>Wynajmuj tak jak chcesz</h2>
                                </div>
                                <div>
                                    <div>
                                        <IonText color="secondary" className="ion-padding">
                                            <h4>Szukasz osoby do dzielenia czynszu?</h4>
                                        </IonText>
                                        <div>
                                            Lubisz porządek i cisze, albo może wolisz mieć komapana do gotowania?
                                            Na podstawie profilu wybierz kogoś z kim masz najwiecej wspólnego
                                            i na pewno się dogadasz!
                                        </div>
                                    </div>
                                    <div>
                                        <IonText color="secondary" className="ion-padding">
                                            <h4>Elastyczne warunki</h4>
                                        </IonText>
                                        <div>
                                            Masz pusty pokój który mógł byś komuś wynająć?
                                            Nie ma znaczenia czy tylko na 3 miesiące, czy może na cały rok!
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div
                            className="col-10 col-md-6 order-first order-lg-last d-flex justify-content-center align-items-center">
                            <div>
                                <img src="assets/cozy_room.jpg" alt="cozy room" className="img-fluid"/>
                            </div>
                        </div>
                    </div>

                    <div className="row my-5 pb-3">
                        <div className="col-10 col-md-6 d-flex justify-content-center align-items-center">
                            <div>
                                <img src="assets/study_room.jpg" alt="study room" className="img-fluid"/>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="d-flex flex-column justify-content-center h-100">
                                <div className="mt-4 mt-lg-0">
                                    <h2>Łatwo i bezpiecznie</h2>
                                </div>
                                <div>
                                    <div>
                                        <IonText color="tertiary" className="ion-padding">
                                            <h4>Appka webowa i mobilna</h4>
                                        </IonText>
                                        <div>
                                            Korzystaj z naszego rozwiązania zarówno w telefonie jak i na komputerze.
                                            Gdziekolwiek jesteś i jak Ci wygodniej :)
                                        </div>
                                    </div>
                                    <div>
                                        <IonText color="tertiary" className="ion-padding">
                                            <h4>Bezpieczne Płatności</h4>
                                        </IonText>
                                        <div>
                                            Dzięki naszemu systemowi będziesz miał pewność,
                                            że czynsz dotrze na czas do Ciebie
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </IonContent>
        </>
    );
};

export default WelcomePage;