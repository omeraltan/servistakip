import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ProjeTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.projeTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  projediInput: ElementFinder = element(by.css('input#proje-temel-projedi'));
  projeKoduInput: ElementFinder = element(by.css('input#proje-temel-projeKodu'));
  aciklamaInput: ElementFinder = element(by.css('input#proje-temel-aciklama'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setProjediInput(projedi) {
    await this.projediInput.sendKeys(projedi);
  }

  async getProjediInput() {
    return this.projediInput.getAttribute('value');
  }

  async setProjeKoduInput(projeKodu) {
    await this.projeKoduInput.sendKeys(projeKodu);
  }

  async getProjeKoduInput() {
    return this.projeKoduInput.getAttribute('value');
  }

  async setAciklamaInput(aciklama) {
    await this.aciklamaInput.sendKeys(aciklama);
  }

  async getAciklamaInput() {
    return this.aciklamaInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setProjediInput('projedi');
    await waitUntilDisplayed(this.saveButton);
    await this.setProjeKoduInput('projeKodu');
    await waitUntilDisplayed(this.saveButton);
    await this.setAciklamaInput('aciklama');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
