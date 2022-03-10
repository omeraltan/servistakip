import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class KurumTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.kurumTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  bakanlikAdiInput: ElementFinder = element(by.css('input#kurum-temel-bakanlikAdi'));
  birimAdiInput: ElementFinder = element(by.css('input#kurum-temel-birimAdi'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBakanlikAdiInput(bakanlikAdi) {
    await this.bakanlikAdiInput.sendKeys(bakanlikAdi);
  }

  async getBakanlikAdiInput() {
    return this.bakanlikAdiInput.getAttribute('value');
  }

  async setBirimAdiInput(birimAdi) {
    await this.birimAdiInput.sendKeys(birimAdi);
  }

  async getBirimAdiInput() {
    return this.birimAdiInput.getAttribute('value');
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
    await this.setBakanlikAdiInput('bakanlikAdi');
    await waitUntilDisplayed(this.saveButton);
    await this.setBirimAdiInput('birimAdi');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
